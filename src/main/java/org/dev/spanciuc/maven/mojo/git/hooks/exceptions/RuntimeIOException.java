package org.dev.spanciuc.maven.mojo.git.hooks.exceptions;

import java.io.IOException;

/**
 * An unchecked wrapper for IOException.
 * <p>
 * see {@link IOException}
 */
public class RuntimeIOException extends RuntimeException {

    /**
     * Creates an instance with a message and a cause.
     *
     * @param message the message.
     * @param cause   the cause.
     */
    public RuntimeIOException(String message, IOException cause) {
        super(message, cause);
    }
}
