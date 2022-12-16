package org.dev.spanciuc.maven.mojo.githooks;

import org.dev.spanciuc.maven.mojo.githooks.commit_msg.CommitMsgParameters;
import org.dev.spanciuc.maven.mojo.githooks.exceptions.GitHooksAreDisabledException;
import org.dev.spanciuc.maven.mojo.githooks.exceptions.NoEnabledGitHooksFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class GitHooksConfigurationTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "      "})
    void constructor_whenInvalidParameterGitDirectoryName_shouldThrowException(
            String gitDirectoryFileName) {
        GitHooksParameters parameters = spy(buildValidGitHooksParameters());
        when(parameters.getGitDirectoryName()).thenReturn(gitDirectoryFileName);
        assertThrows(IllegalArgumentException.class, () -> new GitHooksConfiguration(parameters));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "      "})
    void constructor_whenInvalidParameterGitHooksDirectoryName_shouldThrowException(
            String gitHooksDirectoryFileName) {
        GitHooksParameters parameters = spy(buildValidGitHooksParameters());
        when(parameters.getGitHooksDirectoryName()).thenReturn(gitHooksDirectoryFileName);
        assertThrows(IllegalArgumentException.class, () -> new GitHooksConfiguration(parameters));
    }

    @Test
    void constructor_whenNullParameters_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new GitHooksConfiguration(null));
    }

    @Test
    void constructor_whenValidParameters_shouldCreateConfigurationUsingGivenParameters() {
        String gitDirectoryName = "  gitFolder    ";
        String gitHooksDirectoryName = "  gitHooks Folder    ";
        boolean isEnabled = true;
        CommitMsgParameters commitMsgParameters =
                new CommitMsgParameters(true, "fileName", 100, "t-1, t-2");
        GitHooksParameters parameters =
                new GitHooksParameters(true, gitDirectoryName, gitHooksDirectoryName,
                        commitMsgParameters);

        GitHooksConfiguration configuration = new GitHooksConfiguration(parameters);

        assertEquals(gitDirectoryName.strip(), configuration.getGitDirectoryName());
        assertEquals(gitHooksDirectoryName.strip(), configuration.getGitHooksDirectoryName());
        assertEquals(isEnabled, configuration.isEnabled());
    }

    @Test
    void getEnabledHooksGenerators_whenGitHooksAreDisabled_shouldThrowException() {
        GitHooksParameters parameters = spy(buildValidGitHooksParameters());
        when(parameters.isEnabled()).thenReturn(false);
        GitHooksConfiguration configuration = new GitHooksConfiguration(parameters);
        assertThrows(GitHooksAreDisabledException.class, configuration::getEnabledHooksGenerators);
    }

    @Test
    void getEnabledHooksGenerators_whenIsEnabledAndHasEnabledHooks_shouldReturnUnmodifiableGeneratorsList()
            throws GitHooksAreDisabledException, NoEnabledGitHooksFoundException {
        GitHooksParameters parameters = spy(buildValidGitHooksParameters());
        CommitMsgParameters commitMsgParameters = spy(parameters.getCommitMsg());
        when(parameters.isEnabled()).thenReturn(true);
        when(parameters.getCommitMsg()).thenReturn(commitMsgParameters);
        when(commitMsgParameters.isEnabled()).thenReturn(true);
        GitHooksConfiguration configuration = new GitHooksConfiguration(parameters);
        List<GitHookFileGenerator> generators = configuration.getEnabledHooksGenerators();
        assertEquals(1, generators.size());
        assertNotNull(generators.get(0));
        assertThrows(UnsupportedOperationException.class, () -> generators.add(null));
        assertThrows(UnsupportedOperationException.class, generators::clear);
    }

    @Test
    void getEnabledHooksGenerators_whenNoEnabledGitHooks_shouldThrowException() {
        GitHooksParameters parameters = spy(buildValidGitHooksParameters());
        CommitMsgParameters commitMsgParameters = spy(parameters.getCommitMsg());
        when(parameters.isEnabled()).thenReturn(true);
        when(parameters.getCommitMsg()).thenReturn(commitMsgParameters);
        when(commitMsgParameters.isEnabled()).thenReturn(false);
        GitHooksConfiguration configuration = new GitHooksConfiguration(parameters);
        assertThrows(NoEnabledGitHooksFoundException.class,
                configuration::getEnabledHooksGenerators);
    }

    @Test
    void hasEnabledHooks_whenHasEnabledGitHooksConfiguration_shouldReturnTrue() {
        CommitMsgParameters commitMsgParameters =
                new CommitMsgParameters(true, "fileName", 100, "t-1, t-2");
        GitHooksParameters parameters =
                new GitHooksParameters(true, "folderName", "folderName", commitMsgParameters);
        GitHooksConfiguration configuration = new GitHooksConfiguration(parameters);
        assertTrue(configuration.hasEnabledHooks());
    }

    @Test
    void hasEnabledHooks_whenHasNotEnabledGitHooksConfiguration_shouldReturnFalse() {
        CommitMsgParameters commitMsgParameters =
                new CommitMsgParameters(false, "fileName", 100, "t-1, t-2");
        GitHooksParameters parameters =
                new GitHooksParameters(true, "folderName", "folderName", commitMsgParameters);
        GitHooksConfiguration configuration = new GitHooksConfiguration(parameters);
        assertFalse(configuration.hasEnabledHooks());
    }

    private static GitHooksParameters buildValidGitHooksParameters() {
        return new GitHooksParameters(true, "gitDirectory", "gitHooksDirectory",
                new CommitMsgParameters(true, "fileName", -1, "type-1, type-2"));
    }
}