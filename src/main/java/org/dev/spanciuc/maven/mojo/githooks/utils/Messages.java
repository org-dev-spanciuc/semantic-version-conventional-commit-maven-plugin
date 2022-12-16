package org.dev.spanciuc.maven.mojo.githooks.utils;

/**
 * Utility class for constant messages.
 */
public class Messages {


    /**
     * {@value}
     */
    public static final String MUST_NOT_BE_NULL_MESSAGE = "Must not be null.";

    /**
     * {@value}
     */
    public static final String THE_GIT_HOOKS_ARE_DISABLED_MESSAGE =
            "Git hooks are disabled. Skipping hooks generation...";
    /**
     * {@value}
     */
    public static final String NO_ENABLED_GIT_HOOKS_FOUND_MESSAGE =
            "No enabled git hooks found. Skipping hooks generation...";

    /**
     * {@value}
     */
    public static final String MESSAGE_VALUE_MUST_NOT_BE_NULL = "%s must not be null.";
    /**
     * {@value}
     */
    public static final String MESSAGE_VALUE_MUST_NOT_BE_EMPTY = "%s must not be empty";
    /**
     * {@value}
     */
    public static final String VALUE_GIT_HOOKS_PARAMETERS = "Git hooks parameters";
    /**
     * {@value}
     */
    public static final String VALUE_GIT_DIRECTORY_NAME = "Git directory name";
    /**
     * {@value}
     */
    public static final String VALUE_GIT_HOOKS_DIRECTORY_NAME = "Git hooks directory name";
    /**
     * {@value}
     */
    public static final String STARTING_CHECKS_MESSAGE = "Starting checks...";
    /**
     * {@value}
     */
    public static final String TYPE_BASEDIR = "Basedir";
    /**
     * {@value}
     */
    public static final String TYPE_GIT_HOOKS_CONFIGURATION = "Git hooks configuration";
    /**
     * {@value}
     */
    public static final String CREATED_NUMBER_OF_HOOKS_MESSAGE = "Created %d git hook(s).";
    /**
     * {@value}
     */
    public static final String STARTING_HOOKS_GENERATION_MESSAGE = "Starting hooks generation...";
    /**
     * {@value}
     */
    public static final String NOT_A_GIT_REPOSITORY_MESSAGE = "Not a git repository";
    /**
     * {@value}
     */
    public static final String NOT_A_DIRECTORY_MESSAGE = "Not a directory";
    /**
     * {@value}
     */
    public static final String FOUND_EXISTENT_FILE_MESSAGE = "Found existent file: %s";
    /**
     * {@value}
     */
    public static final String CREATED_NEW_DIRECTORY_MESSAGE = "Created new directory: %s";
    /**
     * {@value}
     */
    public static final String SUCCESSFULLY_PASSED_ALL_CHECKS_MESSAGE =
            "Successfully passed all checks.";
    /**
     * {@value}
     */
    public static final String FOUND_NUMBER_OF_ENABLED_HOOKS_MESSAGE = "Found %d enabled hook(s).";
    /**
     * {@value}
     */
    public static final String HOOK_BY_NAME_SUCCESSFULLY_GENERATED_MESSAGE =
            "Hook: '%s' successfully generated.";
    /**
     * {@value}
     */
    public static final String IS_GIT_REPOSITORY_CHECK_MESSAGE = "- Is git repository check: %s";
    /**
     * {@value}
     */
    public static final String GIT_HOOKS_DIRECTORY_CHECK_MESSAGE =
            "- Git hooks directory check: %s";
    /**
     * {@value}
     */
    public static final String CHECK_STATUS_SUCCESS = "SUCCESS";
    /**
     * {@value}
     */
    public static final String CHECK_STATUS_FAIL = "FAIL";
    /**
     * {@value}
     */
    public static final String MESSAGE_DUPLICATE_TYPE_NAME =
            "The type name '%s' is defined multiple times.";
    /**
     * {@value}
     */
    public static final String MESSAGE_INVALID_HEADER_MAX_LENGTH_VALUE =
            "The header max length value must be positive integer or -1.";
    /**
     * {@value}
     */
    public static final String VALUE_TYPE_NAME = "Type name";
    /**
     * {@value}
     */
    public static final String VALUE_COMMIT_MESSAGE_PARAMETERS = "Commit message parameters";
    /**
     * {@value}
     */
    public static final String VALUE_FILE_NAME = "File name";
    /**
     * {@value}
     */
    public static final String VALUE_COMMIT_TYPES = "Commit types";
    /**
     * {@value}
     */
    public static final String TYPE_COMMIT_MESSAGE_CONFIGURATION = "Commit message configuration";
    /**
     * {@value}
     */
    public static final String TYPE_PARENT_DIRECTORY = "Parent directory";
    /**
     * {@value}
     */
    public static final String MESSAGE_CALLED_GENERATE_METHOD_ON_DISABLED_HOOK_MESSAGE =
            "Called generate method on disabled hook.";
    /**
     * {@value}
     */
    public static final String TYPE_FILE_NAME = "File name";
    /**
     * {@value}
     */
    public static final String TYPE_FILE_CONTENT = "File content";

    private Messages() {
    }
}
