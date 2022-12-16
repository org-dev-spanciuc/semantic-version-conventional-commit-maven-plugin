import org.dev.spanciuc.maven.mojo.githooks.it.utils.CommitMsgHookTestUtils
import org.dev.spanciuc.maven.mojo.githooks.it.utils.GitTestUtils

println CommitMsgHookTestUtils.checkCommitMsgHookFile(basedir, ".git/hooks", "commit-msg")
println GitTestUtils.testCommit(basedir)