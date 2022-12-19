package org.dev.spanciuc.maven.mojo.git.hooks;

import org.dev.spanciuc.maven.mojo.git.hooks.commit_msg.CommitMsgConfiguration;
import org.dev.spanciuc.maven.mojo.git.hooks.exceptions.GitHooksAreDisabledException;
import org.dev.spanciuc.maven.mojo.git.hooks.exceptions.NoEnabledGitHooksFoundException;
import org.dev.spanciuc.maven.mojo.git.hooks.utils.Messages;
import org.dev.spanciuc.maven.mojo.git.hooks.utils.ParameterSanitizer;

import java.util.List;

/**
 * A class represents git hooks configuration parameters.
 */
public class GitHooksConfiguration {

    private final boolean enabled;

    private final String gitDirectoryName;

    private final String gitHooksDirectoryName;


    private final List<GitHookConfiguration> gitHooksConfigurations;

    /**
     * Creates a configuration instance with given parameters.
     * <p>
     * This method sanitizes and validates parameters value. In case of invalid parameters, an
     * exception will be thrown.
     *
     * @param gitHooksParameters the parameters.
     * @throws IllegalArgumentException if parameters object is null.
     * @throws IllegalArgumentException if parameters object has invalid git directory name.
     * @throws IllegalArgumentException if parameters object has invalid git hooks directory name.
     * @throws IllegalArgumentException if parameters object has invalid commit-msg parameters.
     */
    public GitHooksConfiguration(GitHooksParameters gitHooksParameters) {
        if (null == gitHooksParameters) {
            throw new IllegalArgumentException(
                    String.format(Messages.MESSAGE_VALUE_MUST_NOT_BE_NULL,
                            Messages.VALUE_GIT_HOOKS_PARAMETERS));
        }
        this.gitDirectoryName = sanitizeGitDirectoryName(gitHooksParameters.getGitDirectoryName());
        this.gitHooksDirectoryName =
                sanitizeGitHooksDirectoryName(gitHooksParameters.getGitHooksDirectoryName());
        this.enabled = gitHooksParameters.isEnabled();
        this.gitHooksConfigurations =
                List.of(new CommitMsgConfiguration(gitHooksParameters.getCommitMsg()));
    }

    /**
     * Gets enabled hooks generators.
     * <p>
     * Checks if git hooks are enabled and has at least one enabled git hook. If so return an
     * unmodifiable list of generators.
     *
     * @return generator's list.
     * @throws GitHooksAreDisabledException    if git hooks are disabled.
     * @throws NoEnabledGitHooksFoundException if no enabled git hooks found.
     */
    public List<GitHookFileGenerator> getEnabledHooksGenerators()
            throws GitHooksAreDisabledException, NoEnabledGitHooksFoundException {
        if (!isEnabled()) {
            throw new GitHooksAreDisabledException();
        }
        if (!hasEnabledHooks()) {
            throw new NoEnabledGitHooksFoundException();
        }
        return gitHooksConfigurations.stream()
                .filter(GitHookConfiguration::isEnabled)
                .map(GitHookConfiguration::getGitHookFileGenerator)
                .toList();
    }

    /**
     * Gets the git directory name
     *
     * @return git directory name.
     */
    public String getGitDirectoryName() {
        return gitDirectoryName;
    }

    /**
     * Gets the git hooks directory name
     *
     * @return git hooks directory name.
     */
    public String getGitHooksDirectoryName() {
        return gitHooksDirectoryName;
    }

    /**
     * Checks if at least one hook is enabled.
     *
     * @return true if at least one hook is enabled, otherwise - false.
     */
    public boolean hasEnabledHooks() {
        return gitHooksConfigurations.stream().anyMatch(GitHookConfiguration::isEnabled);
    }

    /**
     * Checks  if git hooks are globally enabled.
     *
     * @return true if enabled, otherwise - false.
     */
    public boolean isEnabled() {
        return enabled;
    }

    private String sanitizeGitDirectoryName(String gitDirectoryName) {
        return ParameterSanitizer.sanitizeRequiredStringParameter(gitDirectoryName,
                Messages.VALUE_GIT_DIRECTORY_NAME);
    }

    private String sanitizeGitHooksDirectoryName(String gitHooksDirectoryName) {
        return ParameterSanitizer.sanitizeRequiredStringParameter(gitHooksDirectoryName,
                Messages.VALUE_GIT_HOOKS_DIRECTORY_NAME);
    }

}
