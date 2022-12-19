package org.dev.spanciuc.maven.mojo.git.hooks.commit_msg;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.dev.spanciuc.maven.mojo.git.hooks.GitHookConfiguration;
import org.dev.spanciuc.maven.mojo.git.hooks.GitHookFileGenerator;
import org.dev.spanciuc.maven.mojo.git.hooks.utils.Messages;
import org.dev.spanciuc.maven.mojo.git.hooks.utils.ParameterSanitizer;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A class represents a commit-msg hook configuration.
 */
@Value
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CommitMsgConfiguration implements GitHookConfiguration {

    /**
     * The character used as delimiter for the type parameter.
     */
    public static final String TYPES_SPLIT_CHAR = ",";

    /**
     * The value represents unrestricted header max length.
     */
    public static final int UNRESTRICTED_HEADER_MAX_LENGTH_VALUE = -1;
    static final int HEADER_MAX_LENGTH_MIN_ALLOWED_VALUE = 1;

    /**
     * Whether the hook is enabled.
     *
     * @return true if enabled, otherwise - false.
     */
    @SuppressWarnings("JavadocDeclaration")
    boolean enabled;

    /**
     * The hook's file name.
     *
     * @return fileName.
     */
    @SuppressWarnings("JavadocDeclaration")
    String fileName;


    /**
     * The max allowed length for commit header. For unrestricted length
     * use:{@value
     * CommitMsgConfiguration#UNRESTRICTED_HEADER_MAX_LENGTH_VALUE}
     *
     * @return max allowed length.
     */
    @SuppressWarnings("JavadocDeclaration")
    int headerMaxLength;

    /**
     * The list of allowed commit types as comma separated values.
     *
     * @return allowed commit types.
     */
    @SuppressWarnings("JavadocDeclaration")
    SortedSet<String> types;

    /**
     * Creates an instance with the given parameters
     * <p>
     * This method sanitizes and validates the parameters.
     * <p>
     * For header max length parameter the valid value must be either
     * {@value #UNRESTRICTED_HEADER_MAX_LENGTH_VALUE} or a positive integer number greater than 0.
     *
     * @param commitMsgParameters the parameters.
     * @throws IllegalArgumentException if parameters object is null.
     * @throws IllegalArgumentException if parameters object has invalid file name.
     * @throws IllegalArgumentException if parameters object has invalid header max length.
     * @throws IllegalArgumentException if parameters object has invalid types.
     */
    public CommitMsgConfiguration(CommitMsgParameters commitMsgParameters) {
        if (null == commitMsgParameters) {
            throw new IllegalArgumentException(
                    String.format(Messages.MESSAGE_VALUE_MUST_NOT_BE_NULL,
                            Messages.VALUE_COMMIT_MESSAGE_PARAMETERS));
        }
        this.fileName = sanitizeFileName(commitMsgParameters.getFileName());
        this.enabled = commitMsgParameters.isEnabled();
        this.headerMaxLength = sanitizeHeaderMaxLength(commitMsgParameters.getHeaderMaxLength());
        this.types = sanitizeTypes(commitMsgParameters.getTypes());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GitHookFileGenerator getGitHookFileGenerator() {
        return new BashScriptCommitMsgHookGenerator(this);
    }

    /**
     * Gets an unmodifiable sorted set of allowed commit types.
     *
     * @return allowed commit types.
     */
    public Set<String> getTypes() {
        return Collections.unmodifiableSortedSet(types);
    }

    /**
     * Checks if commit header has length restriction.
     *
     * @return true if it has restriction, otherwise - false.
     */
    public boolean hasHeaderMaxLengthRestriction() {
        return headerMaxLength != UNRESTRICTED_HEADER_MAX_LENGTH_VALUE;
    }

    private String sanitizeFileName(String fileName) {
        return ParameterSanitizer.sanitizeRequiredStringParameter(fileName,
                Messages.VALUE_FILE_NAME);
    }

    private int sanitizeHeaderMaxLength(int headerMaxLength) {
        if (headerMaxLength != UNRESTRICTED_HEADER_MAX_LENGTH_VALUE &&
                headerMaxLength < HEADER_MAX_LENGTH_MIN_ALLOWED_VALUE) {
            throw new IllegalArgumentException(Messages.MESSAGE_INVALID_HEADER_MAX_LENGTH_VALUE);
        }
        return headerMaxLength;
    }

    private String sanitizeType(String type) {
        return ParameterSanitizer.sanitizeRequiredStringParameter(type, Messages.VALUE_TYPE_NAME);
    }

    private SortedSet<String> sanitizeTypes(String types) {
        if (null == types) {
            throw new IllegalArgumentException(
                    String.format(Messages.MESSAGE_VALUE_MUST_NOT_BE_NULL,
                            Messages.VALUE_COMMIT_TYPES));
        }
        if (types.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format(Messages.MESSAGE_VALUE_MUST_NOT_BE_EMPTY,
                            Messages.VALUE_COMMIT_TYPES));
        }
        SortedSet<String> result = new TreeSet<>();
        for (String type : types.split(TYPES_SPLIT_CHAR)) {
            String sanitizedType = sanitizeType(type);
            if (!result.add(sanitizedType)) {
                throw new IllegalArgumentException(
                        String.format(Messages.MESSAGE_DUPLICATE_TYPE_NAME, sanitizedType));

            }
        }
        return result;
    }

}
