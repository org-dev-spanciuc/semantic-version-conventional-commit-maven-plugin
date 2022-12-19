package org.dev.spanciuc.maven.mojo.git.hooks.it.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

/**
 * Utility class for managing project's git repository.
 */
@SuppressWarnings("unused") // called form groovy
public class GitTestUtils {

    static final String MESSAGE_INITIALIZED_GIT_REPOSITORY = "Initialized git repository: %s";

    /**
     * Creates a git repository in current project's folder.
     *
     * @param basedir          the project directory.
     * @param gitDirectoryName git directory folder.
     * @return a message that confirms the repository initialization.
     * @throws GitAPIException in case any Git exception occur.
     */
    public static String initRepository(File basedir, String gitDirectoryName)
            throws GitAPIException {
        File gitDirectory = new File(basedir, gitDirectoryName);
        Git git = Git.init().setGitDir(gitDirectory).call();
        return String.format(MESSAGE_INITIALIZED_GIT_REPOSITORY, git);
    }
}
