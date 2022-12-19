package org.dev.spanciuc.maven.mojo.git.hooks.exceptions;

import org.dev.spanciuc.maven.mojo.git.hooks.utils.Messages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoEnabledGitHooksFoundExceptionTest {

    @Test
    void constructor_whenMessageParam_shouldCreateExceptionWithGivenMessage() {
        String message = "Some message";
        NoEnabledGitHooksFoundException exception = new NoEnabledGitHooksFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructor_whenNoParams_shouldCreateExceptionWithDefaultMessage() {
        NoEnabledGitHooksFoundException exception = new NoEnabledGitHooksFoundException();
        assertEquals(Messages.NO_ENABLED_GIT_HOOKS_FOUND_MESSAGE, exception.getMessage());
    }

}