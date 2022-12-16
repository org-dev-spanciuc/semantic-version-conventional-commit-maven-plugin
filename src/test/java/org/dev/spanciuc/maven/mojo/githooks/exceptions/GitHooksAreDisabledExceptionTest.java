package org.dev.spanciuc.maven.mojo.githooks.exceptions;

import org.dev.spanciuc.maven.mojo.githooks.utils.Messages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GitHooksAreDisabledExceptionTest {

    @Test
    void constructor_whenMessageParam_shouldCreateExceptionWithGivenMessage() {
        String message = "Some message";
        GitHooksAreDisabledException exception = new GitHooksAreDisabledException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructor_whenNoParams_shouldCreateExceptionWithDefaultMessage() {
        GitHooksAreDisabledException exception = new GitHooksAreDisabledException();
        assertEquals(Messages.THE_GIT_HOOKS_ARE_DISABLED_MESSAGE, exception.getMessage());
    }
}