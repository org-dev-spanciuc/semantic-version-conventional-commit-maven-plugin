package org.dev.spanciuc.maven.mojo.git.hooks.commit_msg;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommitMsgParametersTest {
    @Test
    void constructor_whenNoParameters_shouldCreateInstanceWithDefaultValues() {
        CommitMsgParameters commitMsgParameters = new CommitMsgParameters();
        Assertions.assertEquals(CommitMsgParameters.DEFAULT_FILE_NAME,
                commitMsgParameters.getFileName());
        Assertions.assertEquals(CommitMsgParameters.DEFAULT_ENABLED, commitMsgParameters.isEnabled());
        Assertions.assertEquals(CommitMsgParameters.DEFAULT_HEADER_MAX_LENGTH,
                commitMsgParameters.getHeaderMaxLength());
        Assertions.assertEquals(CommitMsgParameters.DEFAULT_TYPES, commitMsgParameters.getTypes());
    }
}