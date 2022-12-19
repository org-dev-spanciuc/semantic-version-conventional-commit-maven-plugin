import org.dev.spanciuc.maven.mojo.git.hooks.it.utils.CommitMsgHookTestUtils

println CommitMsgHookTestUtils.checkCommitMsgHookFile(basedir, ".git/hooks", "commit-msg")