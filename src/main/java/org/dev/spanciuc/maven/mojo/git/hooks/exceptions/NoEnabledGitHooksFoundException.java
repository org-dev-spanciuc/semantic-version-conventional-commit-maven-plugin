package org.dev.spanciuc.maven.mojo.git.hooks.exceptions;

import org.dev.spanciuc.maven.mojo.git.hooks.utils.Messages;

/**
 * Thrown when trying to interact with git hooks, but no individual enabled git hooks were found.
 */
public class NoEnabledGitHooksFoundException extends Exception {

    /**
     * Creates an instance with default message.
     */
    public NoEnabledGitHooksFoundException() {
        super(Messages.NO_ENABLED_GIT_HOOKS_FOUND_MESSAGE);
    }

    /**
     * Creates an instance with a given message.
     *
     * @param message the message.
     */
    public NoEnabledGitHooksFoundException(String message) {
        super(message);
    }
}
