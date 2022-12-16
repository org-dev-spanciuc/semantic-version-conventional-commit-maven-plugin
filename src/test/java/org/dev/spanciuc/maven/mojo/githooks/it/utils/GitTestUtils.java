package org.dev.spanciuc.maven.mojo.githooks.it.utils;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.hooks.CommitMsgHook;
import org.eclipse.jgit.hooks.Hooks;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Utility class for managing project's git repository.
 */
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

    public static String testCommit(File basedir)
            throws IOException, GitAPIException {
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        Repository repository = repositoryBuilder.setGitDir(new File(basedir, ".git"))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .setMustExist(true)
                .build();
        Git git = Git.wrap(repository);

        CommitMsgHook commitMsgHook = Hooks.commitMsg(repository, System.out, System.err);

        String initial_commit_result = commitMsgHook.setCommitMessage("initial commit").call();

        AddCommand add = git.add();
        add.addFilepattern(".").call();

        CommitCommand commit = git.commit();
        RevCommit initial_commit = commit.setMessage("initial commit").call();


        return initial_commit_result;
    }
}
