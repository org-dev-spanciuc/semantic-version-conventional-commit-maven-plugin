package org.dev.spanciuc.maven.mojo.githooks;

/**
 * Provides methods for specific git hook configuration.
 */
public interface GitHookConfiguration {

    /**
     * Gets the git hook's file name.
     *
     * @return filename.
     */
    String getFileName();

    /**
     * Gets the git hook's file generator instance.
     *
     * @return file generator.
     */
    GitHookFileGenerator getGitHookFileGenerator();

    /**
     * Checks if the git hook is enabled.
     *
     * @return true if enabled, otherwise - false.
     */
    boolean isEnabled();

}
