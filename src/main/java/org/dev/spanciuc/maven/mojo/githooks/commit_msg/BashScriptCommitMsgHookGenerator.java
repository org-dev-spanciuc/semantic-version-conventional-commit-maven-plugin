package org.dev.spanciuc.maven.mojo.githooks.commit_msg;

import org.dev.spanciuc.maven.mojo.githooks.GitHookFileGenerator;
import org.dev.spanciuc.maven.mojo.githooks.exceptions.RuntimeIOException;
import org.dev.spanciuc.maven.mojo.githooks.utils.Messages;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class representing bash script commit-msg hook generator.
 * <p>
 * It's used to create a hook file in a given directory.
 */
public class BashScriptCommitMsgHookGenerator implements GitHookFileGenerator {

    private final CommitMsgConfiguration commitMessageConfiguration;

    /**
     * Creates a generator instance for given configuration.
     *
     * @param commitMessageConfiguration the commit-msg hook configuration.
     * @throws IllegalArgumentException if configuration object is null.
     */
    public BashScriptCommitMsgHookGenerator(CommitMsgConfiguration commitMessageConfiguration) {
        if (null == commitMessageConfiguration) {
            throw new IllegalArgumentException(
                    String.format(Messages.MESSAGE_VALUE_MUST_NOT_BE_NULL,
                            Messages.TYPE_COMMIT_MESSAGE_CONFIGURATION));
        }
        this.commitMessageConfiguration = commitMessageConfiguration;
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException if trying to generate a disabled hook.
     * @throws IllegalStateException if parentDirectory is null.
     */
    @Override
    public File generate(File parentDirectory) {
        if (!commitMessageConfiguration.isEnabled()) {
            throw new IllegalStateException(
                    Messages.MESSAGE_CALLED_GENERATE_METHOD_ON_DISABLED_HOOK_MESSAGE);
        }
        if (null == parentDirectory) {
            throw new IllegalArgumentException(
                    String.format(Messages.MESSAGE_VALUE_MUST_NOT_BE_NULL,
                            Messages.TYPE_PARENT_DIRECTORY));
        }
        String scriptContent = generateScriptContent();
        return generateScriptFile(parentDirectory, scriptContent);
    }

    /**
     * Gets the commit-msg hook filename.
     *
     * @return the filename.
     */
    @Override
    public String getFileName() {
        return commitMessageConfiguration.getFileName();
    }

    private String generateScriptContent() {
        return BashScriptContentBuilder.build(commitMessageConfiguration);
    }

    private File generateScriptFile(File parentDirectory, String scriptContent) {
        return BashScriptFileWriter.createAndWrite(parentDirectory,
                commitMessageConfiguration.getFileName(), scriptContent);
    }

    static final class BashScriptFileWriter {

        private BashScriptFileWriter() {
        }

        public static File createAndWrite(File parentDirectory, String fileName,
                                          String fileContent) {
            if (null == parentDirectory) {
                throw new IllegalArgumentException(
                        String.format(Messages.MESSAGE_VALUE_MUST_NOT_BE_NULL,
                                Messages.TYPE_PARENT_DIRECTORY));
            }
            if (null == fileName) {
                throw new IllegalArgumentException(
                        String.format(Messages.MESSAGE_VALUE_MUST_NOT_BE_NULL,
                                Messages.TYPE_FILE_NAME));
            }

            if (null == fileContent) {
                throw new IllegalArgumentException(
                        String.format(Messages.MESSAGE_VALUE_MUST_NOT_BE_NULL,
                                Messages.TYPE_FILE_CONTENT));
            }

            File file = new File(parentDirectory, fileName);
            try (FileWriter fw = new FileWriter(file)) {
                fw.write(fileContent);
            } catch (IOException e) {
                throw new RuntimeIOException(e.getMessage(), e);
            }
            return file;
        }
    }

    static final class BashScriptContentBuilder {

        public static final String LINE_SEPARATOR = "\n";
        private static final String TEMPLATE_BASH_SCRIPT_HEADER = "#!/usr/bin/env bash";
        private static final String TEMPLATE_COMMIT_TYPES_DELIMITER = "|";
        private static final String TEMPLATE_COMMIT_MESSAGE_CHECK = """
                if ! head -1 "$1" | grep -qE "^(%s)(\\(.+?\\))?!?: .{1,}$"; then
                    echo "Aborting commit. Your commit message is invalid." >&2
                    cat "$1"
                    exit 1
                fi""";
        private static final String TEMPLATE_COMMIT_MESSAGE_HEADER_LENGTH_CHECK = """
                if ! head -1 "$1" | grep -qE "^.{1,%s}$"; then
                    echo "Aborting commit. Your commit message is too long. Max %s characters allowed." >&2
                    exit 1
                fi""";

        private BashScriptContentBuilder() {
        }

        public static String build(CommitMsgConfiguration commitMsgConfiguration) {

            if (null == commitMsgConfiguration) {
                throw new IllegalArgumentException(Messages.MUST_NOT_BE_NULL_MESSAGE);
            }

            StringBuilder scriptContent = new StringBuilder(TEMPLATE_BASH_SCRIPT_HEADER);
            scriptContent.append(LINE_SEPARATOR);
            scriptContent.append(String.format(TEMPLATE_COMMIT_MESSAGE_CHECK,
                    String.join(TEMPLATE_COMMIT_TYPES_DELIMITER,
                            commitMsgConfiguration.getTypes())));
            if (commitMsgConfiguration.hasHeaderMaxLengthRestriction()) {
                int headerMaxLength = commitMsgConfiguration.getHeaderMaxLength();
                scriptContent.append(LINE_SEPARATOR);
                scriptContent.append(
                        String.format(TEMPLATE_COMMIT_MESSAGE_HEADER_LENGTH_CHECK, headerMaxLength,
                                headerMaxLength));
            }
            return scriptContent.toString();

        }
    }
}
