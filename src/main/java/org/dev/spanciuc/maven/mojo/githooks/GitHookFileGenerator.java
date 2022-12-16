package org.dev.spanciuc.maven.mojo.githooks;

import java.io.File;

/**
 * Provides the necessary methods for git hooks file generation.
 */
public interface GitHookFileGenerator {

    /**
     * Creates a git hook file in directory.
     *
     * @param parentDirectory the target directory.
     * @return generated file.
     */
    File generate(File parentDirectory);

    /**
     * Gets the name of the file.
     *
     * @return filename.
     */
    String getFileName();

}
