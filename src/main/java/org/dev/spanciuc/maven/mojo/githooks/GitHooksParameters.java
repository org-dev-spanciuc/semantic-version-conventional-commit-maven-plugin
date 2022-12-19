package org.dev.spanciuc.maven.mojo.githooks;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.dev.spanciuc.maven.mojo.githooks.commit_msg.CommitMsgParameters;

/**
 * A class to hold git hooks configuration's parameters..
 */
@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class GitHooksParameters {

    /**
     * Default value for git directory name.
     */
    public static final String DEFAULT_GIT_DIRECTORY_NAME = ".git";

    /**
     * Default value for git hooks directory name.
     */
    public static final String DEFAULT_GIT_HOOKS_DIRECTORY_NAME = ".git/hooks";


    /**
     * Default value for enabled git hooks by default represented as string.
     */
    public static final String DEFAULT_ENABLED_AS_STRING = "true";

    /**
     * Default value for enabled git hooks by default.
     */
    @SuppressWarnings("ConstantConditions")
    public static final boolean DEFAULT_ENABLED = Boolean.parseBoolean(DEFAULT_ENABLED_AS_STRING);

    /**
     * Default commit-msg hook parameters.
     */
    public static final CommitMsgParameters DEFAULT_COMMIT_MSG_PARAMETERS =
            new CommitMsgParameters();

    /**
     * Whether git hooks are enabled.
     *
     * @return true if enabled, otherwise - false.
     */
    @SuppressWarnings("JavadocDeclaration")
    boolean enabled;

    /**
     * The git directory name.
     *
     * @return directory name.
     */
    @SuppressWarnings("JavadocDeclaration")
    String gitDirectoryName;

    /**
     * The git hook's directory name.
     *
     * @return git hook's directory name.
     */
    @SuppressWarnings("JavadocDeclaration")
    String gitHooksDirectoryName;

    /**
     * The commit message hook parameters.
     *
     * @return commit message parameters.
     */
    @SuppressWarnings("JavadocDeclaration")
    CommitMsgParameters commitMsg;

    /**
     * Creates an instance with default values.
     */
    public GitHooksParameters() {
        this(DEFAULT_ENABLED, DEFAULT_GIT_DIRECTORY_NAME, DEFAULT_GIT_HOOKS_DIRECTORY_NAME,
                DEFAULT_COMMIT_MSG_PARAMETERS);
    }
}
