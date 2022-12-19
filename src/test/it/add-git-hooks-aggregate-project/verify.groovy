import org.dev.spanciuc.maven.mojo.githooks.it.utils.CommitMsgHookTestUtils

println CommitMsgHookTestUtils.checkCommitMsgHookFile(basedir, ".git/hooks", "commit-msg")