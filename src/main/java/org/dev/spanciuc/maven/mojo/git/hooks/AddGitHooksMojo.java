package org.dev.spanciuc.maven.mojo.git.hooks;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.dev.spanciuc.maven.mojo.git.hooks.commit_msg.CommitMsgParameters;
import org.dev.spanciuc.maven.mojo.git.hooks.exceptions.GitHooksAreDisabledException;
import org.dev.spanciuc.maven.mojo.git.hooks.exceptions.NoEnabledGitHooksFoundException;
import org.dev.spanciuc.maven.mojo.git.hooks.utils.Messages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A Mojo class to add git hooks to the project.
 */
@Mojo(name = "add-git-hooks", defaultPhase = LifecyclePhase.INITIALIZE)
public class AddGitHooksMojo extends AbstractMojo {

    /**
     * The project's base dir.
     */
    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File basedir;

    /**
     * Whether git hooks are enabled.
     */
    @Parameter(property = "gitHooksEnabled",
            defaultValue = GitHooksParameters.DEFAULT_ENABLED_AS_STRING)
    private boolean gitHooksEnabled;

    /**
     * The git directory name.
     */
    @Parameter(property = "gitDirectory",
            defaultValue = GitHooksParameters.DEFAULT_GIT_DIRECTORY_NAME)
    private String gitDirectory;

    /**
     * The git hook's directory name.
     */
    @Parameter(property = "gitHooksDirectory",
            defaultValue = GitHooksParameters.DEFAULT_GIT_HOOKS_DIRECTORY_NAME)
    private String gitHooksDirectory;

    /**
     * Whether the hook is enabled.
     */
    @Parameter(property = "commitMsgEnabled",
            defaultValue = CommitMsgParameters.DEFAULT_ENABLED_AS_STRING)
    private boolean commitMsgEnabled;

    /**
     * The hook's file name.
     */
    @Parameter(property = "commitMsgFileName", defaultValue = CommitMsgParameters.DEFAULT_FILE_NAME)
    private String commitMsgFileName;

    /**
     * The max allowed length for commit header. For unrestricted length
     * use:{@value
     * org.dev.spanciuc.maven.mojo.git.hooks.commit_msg.CommitMsgConfiguration#UNRESTRICTED_HEADER_MAX_LENGTH_VALUE}
     */
    @Parameter(property = "commitMsgHeaderMaxLength",
            defaultValue = CommitMsgParameters.DEFAULT_HEADER_MAX_LENGTH_AS_STRING)
    private int commitMsgHeaderMaxLength;

    /**
     * The list of comma separated allowed commit types.
     * <p>
     * Ex. "feat,fix,docs"
     */
    @Parameter(property = "commitMsgTypes", defaultValue = CommitMsgParameters.DEFAULT_TYPES)
    private String commitMsgTypes;

    /**
     * Creates an instance.
     */
    public AddGitHooksMojo() {
        this.gitHooksEnabled = GitHooksParameters.DEFAULT_ENABLED;
        this.gitDirectory = GitHooksParameters.DEFAULT_GIT_DIRECTORY_NAME;
        this.gitHooksDirectory = GitHooksParameters.DEFAULT_GIT_HOOKS_DIRECTORY_NAME;
        this.commitMsgEnabled = CommitMsgParameters.DEFAULT_ENABLED;
        this.commitMsgFileName = CommitMsgParameters.DEFAULT_FILE_NAME;
        this.commitMsgHeaderMaxLength = CommitMsgParameters.DEFAULT_HEADER_MAX_LENGTH;
        this.commitMsgTypes = CommitMsgParameters.DEFAULT_TYPES;
    }

    AddGitHooksMojo(File basedir, GitHooksParameters gitHooksParameters) {
        this();
        this.basedir = basedir;
        this.gitHooksEnabled = gitHooksParameters.isEnabled();
        this.gitDirectory = gitHooksParameters.getGitDirectoryName();
        this.gitHooksDirectory = gitHooksParameters.getGitHooksDirectoryName();
        this.commitMsgEnabled = gitHooksParameters.getCommitMsg().isEnabled();
        this.commitMsgFileName = gitHooksParameters.getCommitMsg().getFileName();
        this.commitMsgHeaderMaxLength = gitHooksParameters.getCommitMsg().getHeaderMaxLength();
        this.commitMsgTypes = gitHooksParameters.getCommitMsg().getTypes();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation generates hook files if and only if:
     * <ul>
     *   <li>current project is a git repository </li>
     *   <li>git hook directory can be created or already exists</li>
     *   <li>git hooks are globally enabled</li>
     *   <li>there are at least one individual enabled git hook</li>
     * </ul>
     * <p>
     * In case when current project is not a git repository or git hooks directory cannot be created,
     * the execution will fail.
     * </p>
     * <p>
     * In case when git hooks are globally disabled or there are no individual git hooks enabled,
     * the execution will succeed but a warning will be displayed to inform that git hooks generation was skipped.
     * </p>
     * <p>
     *  By default generated git hooks files will override existent files in the target directory.
     * </p>
     *
     * @throws IllegalStateException if is not a git repository or git hooks folder cannot be
     *                               created.
     */
    @Override
    public void execute() {

        CommitMsgParameters commitMsgParameters =
                new CommitMsgParameters(commitMsgEnabled, commitMsgFileName,
                        commitMsgHeaderMaxLength, commitMsgTypes);

        GitHooksParameters gitHooksParameters =
                new GitHooksParameters(gitHooksEnabled, gitDirectory, gitHooksDirectory,
                        commitMsgParameters);

        GitHooksConfiguration gitHooksConfiguration = new GitHooksConfiguration(gitHooksParameters);

        List<GitHookFileGenerator> enabledHooksGenerators;

        try {
            enabledHooksGenerators = getEnabledGitHookFileGenerators(gitHooksConfiguration);
        } catch (NoEnabledGitHooksFoundException | GitHooksAreDisabledException e) {
            getLog().warn(e.getMessage());
            getLog().info(String.format(Messages.CREATED_NUMBER_OF_HOOKS_MESSAGE, 0));
            return;
        }

        performChecks(gitHooksConfiguration);
        performHooksGeneration(enabledHooksGenerators,
                gitHooksConfiguration.getGitHooksDirectoryName());
    }

    private void checkGitHooksDirectory(String gitHooksDirectoryName) {
        File gitHooksDir = new File(basedir, gitHooksDirectoryName);

        if (gitHooksDir.exists() && !gitHooksDir.isDirectory()) {
            getLog().warn(String.format(Messages.GIT_HOOKS_DIRECTORY_CHECK_MESSAGE,
                    Messages.CHECK_STATUS_FAIL));
            throw new IllegalStateException(Messages.NOT_A_DIRECTORY_MESSAGE);
        }
        getLog().info(String.format(Messages.GIT_HOOKS_DIRECTORY_CHECK_MESSAGE,
                Messages.CHECK_STATUS_SUCCESS));
    }

    private void checkIsGitRepository(String gitDirectoryName) {
        File gitRepoDirectory = new File(basedir, gitDirectoryName);
        if (!gitRepoDirectory.exists() || !gitRepoDirectory.isDirectory()) {
            getLog().warn(String.format(Messages.IS_GIT_REPOSITORY_CHECK_MESSAGE,
                    Messages.CHECK_STATUS_FAIL));
            throw new IllegalStateException(Messages.NOT_A_GIT_REPOSITORY_MESSAGE);
        }
        getLog().info(String.format(Messages.IS_GIT_REPOSITORY_CHECK_MESSAGE,
                Messages.CHECK_STATUS_SUCCESS));
    }

    private File createOrGetGitHooksDirectory(String gitHooksDirectoryName) {
        File gitHooksDir = new File(basedir, gitHooksDirectoryName);

        if (!gitHooksDir.mkdirs()) {
            getLog().info(
                    String.format(Messages.FOUND_EXISTENT_FILE_MESSAGE, gitHooksDir.getPath()));
        } else {
            getLog().info(
                    String.format(Messages.CREATED_NEW_DIRECTORY_MESSAGE, gitHooksDir.getPath()));
        }
        return gitHooksDir;
    }

    private List<GitHookFileGenerator> getEnabledGitHookFileGenerators(
            GitHooksConfiguration gitHooksConfiguration)
            throws GitHooksAreDisabledException, NoEnabledGitHooksFoundException {

        List<GitHookFileGenerator> enabledHooksGenerators =
                gitHooksConfiguration.getEnabledHooksGenerators();
        getLog().info(String.format(Messages.FOUND_NUMBER_OF_ENABLED_HOOKS_MESSAGE,
                enabledHooksGenerators.size()));
        return enabledHooksGenerators;
    }

    private void performChecks(GitHooksConfiguration gitHooksConfiguration) {
        getLog().info(Messages.STARTING_CHECKS_MESSAGE);
        checkIsGitRepository(gitHooksConfiguration.getGitDirectoryName());
        checkGitHooksDirectory(gitHooksConfiguration.getGitHooksDirectoryName());
        getLog().info(Messages.SUCCESSFULLY_PASSED_ALL_CHECKS_MESSAGE);
    }

    private void performHooksGeneration(List<GitHookFileGenerator> enabledHooksGenerators,
                                        String gitHooksDirectoryName) {

        getLog().info(Messages.STARTING_HOOKS_GENERATION_MESSAGE);

        File targetHooksDirectory = createOrGetGitHooksDirectory(gitHooksDirectoryName);

        List<File> hookFiles = new ArrayList<>();

        for (GitHookFileGenerator hookGenerator : enabledHooksGenerators) {
            File hookFile = hookGenerator.generate(targetHooksDirectory);
            getLog().info(String.format(Messages.HOOK_BY_NAME_SUCCESSFULLY_GENERATED_MESSAGE,
                    hookFile.getPath()));
            hookFiles.add(hookFile);
        }
        getLog().info(String.format(Messages.CREATED_NUMBER_OF_HOOKS_MESSAGE, hookFiles.size()));
    }
}
