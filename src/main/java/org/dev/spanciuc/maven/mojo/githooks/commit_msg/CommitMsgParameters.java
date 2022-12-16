package org.dev.spanciuc.maven.mojo.githooks.commit_msg;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * A class to hold commit message configuration's parameters.
 */
@Value
@AllArgsConstructor
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
    boolean enabled;

    /**
     * The hook's file name.
     *
     * @return fileName.
     */
    String fileName;

    /**
     * The max allowed length for commit header. For unrestricted length
     * use:{@value
     * org.dev.spanciuc.maven.mojo.githooks.commit_msg.CommitMsgConfiguration#UNRESTRICTED_HEADER_MAX_LENGTH_VALUE}
     *
     * @return max allowed length.
     */
    int headerMaxLength;

    /**
     * The list of allowed commit types as comma separated values.
     */
    String types;

    /**
     * Creates an instance with default values.
     */
    public CommitMsgParameters() {
        this(DEFAULT_ENABLED, DEFAULT_FILE_NAME, DEFAULT_HEADER_MAX_LENGTH, DEFAULT_TYPES);
    }
}
