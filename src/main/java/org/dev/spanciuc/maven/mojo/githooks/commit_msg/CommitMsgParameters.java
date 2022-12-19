package org.dev.spanciuc.maven.mojo.githooks.commit_msg;

import lombok.Value;

/**
 * A class to hold commit message configuration's parameters.
 */
@Value
public class CommitMsgParameters {

    /**
     * The default value for file name.
     */
    public static final String DEFAULT_FILE_NAME = "commit-msg";
    /**
     * The default enabled value as string.
     */
    public static final String DEFAULT_ENABLED_AS_STRING = "true";
    /**
     * The default enabled value.
     */
    @SuppressWarnings("ConstantConditions")
    public static final boolean DEFAULT_ENABLED = Boolean.parseBoolean(DEFAULT_ENABLED_AS_STRING);
    /**
     * The default header max length as string.
     */
    public static final String DEFAULT_HEADER_MAX_LENGTH_AS_STRING = "150";
    /**
     * The default header max length.
     */
    public static final int DEFAULT_HEADER_MAX_LENGTH =
            Integer.parseInt(DEFAULT_HEADER_MAX_LENGTH_AS_STRING);
    /**
     * The default commit types represented as a comma separated strings.
     */
    public static final String DEFAULT_TYPES =
            "feat,fix,chore,refactor,docs,style,test,perf,revert,merge";
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
     * org.dev.spanciuc.maven.mojo.githooks.commit_msg.CommitMsgConfiguration#UNRESTRICTED_HEADER_MAX_LENGTH_VALUE}
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
    String types;

    /**
     * Creates an instance with parameters.
     *
     * @param enabled         enabled parameter.
     * @param fileName        fileName parameter.
     * @param headerMaxLength headerMaxLength parameter.
     * @param types           types parameter
     */
    public CommitMsgParameters(boolean enabled, String fileName, int headerMaxLength,
                               String types) {
        this.enabled = enabled;
        this.fileName = fileName;
        this.headerMaxLength = headerMaxLength;
        this.types = types;
    }

    /**
     * Creates an instance with default values.
     */
    public CommitMsgParameters() {
        this(DEFAULT_ENABLED, DEFAULT_FILE_NAME, DEFAULT_HEADER_MAX_LENGTH, DEFAULT_TYPES);
    }
}
