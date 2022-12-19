package org.dev.spanciuc.maven.mojo.git.hooks.exceptions;

import org.dev.spanciuc.maven.mojo.git.hooks.utils.Messages;

/**
 * Thrown when trying to interact with git hooks in globally disabled state.
 */
public class GitHooksAreDisabledException extends Exception {

    /**
     * Creates an instance with default message.
     */
    public GitHooksAreDisabledException() {
        super(Messages.THE_GIT_HOOKS_ARE_DISABLED_MESSAGE);
    }

    /**
     * Creates an instance with a given message.
     *
     * @param message the message.
     */
    public GitHooksAreDisabledException(String message) {
        super(message);
    }
}
