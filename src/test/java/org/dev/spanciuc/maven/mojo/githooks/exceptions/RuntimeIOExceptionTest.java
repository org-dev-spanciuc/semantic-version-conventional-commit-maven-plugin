package org.dev.spanciuc.maven.mojo.githooks.exceptions;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuntimeIOExceptionTest {

    @Test
    void constructor_whenMessageAndThrowable_shouldCreateExceptionWithGivenMessageAndThrowable() {
        String message = "message";
        IOException ioException = new IOException();
        RuntimeIOException runtimeIOException = new RuntimeIOException(message, ioException);
        assertEquals(message, runtimeIOException.getMessage());
        assertEquals(ioException, runtimeIOException.getCause());
    }

}