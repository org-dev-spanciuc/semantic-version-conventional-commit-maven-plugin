package org.dev.spanciuc.maven.mojo.githooks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GitHooksParametersTest {
    @Test
    void constructor_whenNoParameters_shouldCreateWithDefaultValues() {
        GitHooksParameters parameters = new GitHooksParameters();
        assertEquals(GitHooksParameters.DEFAULT_GIT_DIRECTORY_NAME,
                parameters.getGitDirectoryName());
        assertEquals(GitHooksParameters.DEFAULT_GIT_HOOKS_DIRECTORY_NAME,
                parameters.getGitHooksDirectoryName());
        assertEquals(GitHooksParameters.DEFAULT_ENABLED, parameters.isEnabled());
        assertEquals(GitHooksParameters.DEFAULT_COMMIT_MSG_PARAMETERS, parameters.getCommitMsg());
    }
}