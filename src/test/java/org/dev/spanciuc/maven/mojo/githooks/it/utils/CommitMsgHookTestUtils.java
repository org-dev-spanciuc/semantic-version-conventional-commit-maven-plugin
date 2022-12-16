package org.dev.spanciuc.maven.mojo.githooks.it.utils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Utility class for commit message hook file check.
 */
public class CommitMsgHookTestUtils {

    static final String MESSAGE_COULD_NOT_FIND_GIT_HOOKS_DIRECTORY =
            "Could not find git hooks directory: %s";
    static final String MESSAGE_COULD_NOT_FIND_COMMIT_MESSAGE_HOOK_FILE =
            "Could not find commit message hook file: %s";
    static final String MESSAGE_COMMIT_MESSAGE_HOOK_FILE_FOUND =
            "Commit message hook file found: %s";

    /**
     * Checks if commit message hook file exists.
     *
     * @param basedir               the project basedir.
     * @param gitHooksDirectoryName the git hook's directory name.
     * @param hookFileName          the commit message hook file name.
     * @return a message that confirms file existence.
     * @throws FileNotFoundException if git hooks directory not found or is not a directory.
     * @throws FileNotFoundException if commit message hook file not found or is not a file.
     */
    public static String checkCommitMsgHookFile(File basedir, String gitHooksDirectoryName,
                                                String hookFileName) throws FileNotFoundException {
        File gitHooksDirectory = new File(basedir, gitHooksDirectoryName);
        if (!gitHooksDirectory.isDirectory()) {
            throw new FileNotFoundException(
                    String.format(MESSAGE_COULD_NOT_FIND_GIT_HOOKS_DIRECTORY, gitHooksDirectory));
        }

        File commitMsgHook = new File(gitHooksDirectory, hookFileName);
        if (!commitMsgHook.isFile()) {
            throw new FileNotFoundException(
                    String.format(MESSAGE_COULD_NOT_FIND_COMMIT_MESSAGE_HOOK_FILE, commitMsgHook));
        }
        return String.format(MESSAGE_COMMIT_MESSAGE_HOOK_FILE_FOUND, commitMsgHook);
    }

}
