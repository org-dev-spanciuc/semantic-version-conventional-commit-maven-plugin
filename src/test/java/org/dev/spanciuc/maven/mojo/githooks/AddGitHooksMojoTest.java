package org.dev.spanciuc.maven.mojo.githooks;

import org.apache.maven.plugin.logging.Log;
import org.dev.spanciuc.maven.mojo.githooks.commit_msg.CommitMsgParameters;
import org.dev.spanciuc.maven.mojo.githooks.utils.Messages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AddGitHooksMojoTest {

    @Test
    void execute_whenChecksPassed_shouldCreateHooksFiles(@TempDir File basedir) {
        CommitMsgParameters commitMsgParameters =
                new CommitMsgParameters(true, "commit-msg", 100, "type");
        String hooksDirectory = "git/hooks";
        String gitDirectory = "git";
        GitHooksParameters parameters =
                new GitHooksParameters(true, gitDirectory, hooksDirectory, commitMsgParameters);
        AddGitHooksMojo mojo = new AddGitHooksMojo(basedir, parameters);
        File gitFolder = new File(basedir, gitDirectory);
        boolean created = gitFolder.mkdir();
        assertTrue(created);
        mojo.execute();
        assertTrue(Files.exists(
                Path.of(basedir.toString(), hooksDirectory, commitMsgParameters.getFileName())
                        .toAbsolutePath()));
    }

    @Test
    void execute_whenGitHooksAreDisabled_shouldDisplayMessageAndExit(@TempDir File basedir) {
        CommitMsgParameters commitMsgParameters =
                new CommitMsgParameters(true, "commit-msg", 100, "type");
        GitHooksParameters parameters =
                new GitHooksParameters(false, "git", "hooks", commitMsgParameters);
        AddGitHooksMojo mojo = new AddGitHooksMojo(basedir, parameters);
        Log log = mock(Log.class);
        mojo.setLog(log);
        mojo.execute();
        verify(log, times(1)).warn(Messages.THE_GIT_HOOKS_ARE_DISABLED_MESSAGE);
        verify(log, times(1)).info(String.format(Messages.CREATED_NUMBER_OF_HOOKS_MESSAGE, 0));
    }

    @Test
    void execute_whenGitHooksDirectoryDoesNotExist_shouldCreateGitHooksDirectory(
            @TempDir File basedir) {
        CommitMsgParameters commitMsgParameters =
                new CommitMsgParameters(true, "commit-msg", 100, "type");
        String hooksDirectory = "hooks";
        String gitDirectory = "git";
        GitHooksParameters parameters =
                new GitHooksParameters(true, gitDirectory, hooksDirectory, commitMsgParameters);
        AddGitHooksMojo mojo = new AddGitHooksMojo(basedir, parameters);
        File gitFolder = new File(basedir, gitDirectory);
        boolean created = gitFolder.mkdir();
        assertTrue(created);
        mojo.execute();
        assertTrue(Files.exists(Path.of(basedir.getPath(), hooksDirectory)));
    }

    @Test
    void execute_whenGitHooksDirectoryExistsButIsNotDirectory_shouldTrowException(
            @TempDir File basedir) throws IOException {
        CommitMsgParameters commitMsgParameters =
                new CommitMsgParameters(true, "git-hooks/commit-msg", 100, "type");
        String hooksDirectory = "git/hooks";
        String gitDirectory = "git";
        GitHooksParameters parameters =
                new GitHooksParameters(true, gitDirectory, hooksDirectory, commitMsgParameters);
        AddGitHooksMojo mojo = new AddGitHooksMojo(basedir, parameters);
        File gitFolder = new File(basedir, gitDirectory);
        boolean gitFolderCreated = gitFolder.mkdir();
        assertTrue(gitFolderCreated);
        File gitHooksFile = new File(basedir, hooksDirectory);
        boolean gitHooksFolderCreated = gitHooksFile.createNewFile();
        assertTrue(gitHooksFolderCreated);
        assertThrows(IllegalStateException.class, mojo::execute);
    }

    @Test
    void execute_whenGitHooksDirectoryExists_shouldUseExistentDirectory(@TempDir File basedir) {
        CommitMsgParameters commitMsgParameters =
                new CommitMsgParameters(true, "commit-msg", 100, "type");
        String hooksDirectory = "hooks";
        String gitDirectory = "git";
        GitHooksParameters parameters =
                new GitHooksParameters(true, gitDirectory, hooksDirectory, commitMsgParameters);
        AddGitHooksMojo mojo = new AddGitHooksMojo(basedir, parameters);
        File gitFolder = new File(basedir, gitDirectory);
        boolean gitFolderCreated = gitFolder.mkdir();
        assertTrue(gitFolderCreated);
        File hooksFolder = new File(basedir, hooksDirectory);
        boolean gitHooksFolderCreated = hooksFolder.mkdir();
        assertTrue(gitHooksFolderCreated);
        mojo.execute();
        assertTrue(Files.exists(Path.of(basedir.getPath(), hooksDirectory)));
    }

    @Test
    void execute_whenGitRepositoryNotFoundOrIsNotDirectory_shouldTrowException(
            @TempDir File basedir) throws IOException {
        CommitMsgParameters commitMsgParameters =
                new CommitMsgParameters(true, "git-hooks/commit-msg", 100, "type");
        String gitDirectoryName = "git";
        GitHooksParameters parameters =
                new GitHooksParameters(true, gitDirectoryName, "hooks", commitMsgParameters);
        AddGitHooksMojo mojo = new AddGitHooksMojo(basedir, parameters);
        assertThrows(IllegalStateException.class, mojo::execute);
        File gitFile = new File(basedir, gitDirectoryName);
        boolean gitFileCreated = gitFile.createNewFile();
        assertTrue(gitFileCreated);
        assertThrows(IllegalStateException.class, mojo::execute);
    }

    @Test
    void execute_whenWhenNoEnabledGitHooksFound_shouldDisplayMessageAndExit(@TempDir File basedir) {
        CommitMsgParameters commitMsgParameters =
                new CommitMsgParameters(false, "git-hooks/commit-msg", 100, "type");
        GitHooksParameters parameters =
                new GitHooksParameters(false, "git", "hooks", commitMsgParameters);
        AddGitHooksMojo mojo = new AddGitHooksMojo(basedir, parameters);
        Log log = mock(Log.class);
        mojo.setLog(log);
        mojo.execute();
        verify(log, times(1)).warn(Messages.THE_GIT_HOOKS_ARE_DISABLED_MESSAGE);
        verify(log, times(1)).info(String.format(Messages.CREATED_NUMBER_OF_HOOKS_MESSAGE, 0));
    }
}