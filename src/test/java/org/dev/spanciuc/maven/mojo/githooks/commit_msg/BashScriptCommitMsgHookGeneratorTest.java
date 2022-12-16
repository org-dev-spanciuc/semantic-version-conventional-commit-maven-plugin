package org.dev.spanciuc.maven.mojo.githooks.commit_msg;

import org.dev.spanciuc.maven.mojo.githooks.exceptions.RuntimeIOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BashScriptCommitMsgHookGeneratorTest {

    @Test
    void constructor_whenNullConfigurationParameter_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> new BashScriptCommitMsgHookGenerator(null));
    }

    @Test
    void generate_whenDisabled_shouldThrowException() {
        CommitMsgConfiguration configuration = mock(CommitMsgConfiguration.class);
        when(configuration.isEnabled()).thenReturn(false);
        BashScriptCommitMsgHookGenerator generator =
                new BashScriptCommitMsgHookGenerator(configuration);
        File file = new File("parent");
        assertThrows(IllegalStateException.class, () -> generator.generate(file));
    }

    @Test
    void generate_whenEnabledAndNullParentDirectory_shouldThrowException() {
        CommitMsgConfiguration configuration = mock(CommitMsgConfiguration.class);
        when(configuration.isEnabled()).thenReturn(true);
        BashScriptCommitMsgHookGenerator generator =
                new BashScriptCommitMsgHookGenerator(configuration);
        assertThrows(IllegalArgumentException.class, () -> generator.generate(null));
    }

    @Test
    void generate_whenEnabledAndNullParentDirectory_shouldThrowException(
            @TempDir File parentFolder) {

        CommitMsgConfiguration configuration = mock(CommitMsgConfiguration.class);
        String filename = "some-file";
        when(configuration.isEnabled()).thenReturn(true);
        when(configuration.getFileName()).thenReturn(filename);
        when(configuration.getTypes()).thenReturn(new TreeSet<>(List.of("1", "2", "3")));
        when(configuration.hasHeaderMaxLengthRestriction()).thenReturn(true);
        when(configuration.getHeaderMaxLength()).thenReturn(50);
        BashScriptCommitMsgHookGenerator generator =
                new BashScriptCommitMsgHookGenerator(configuration);

        File generatedFile = generator.generate(parentFolder);

        assertTrue(generatedFile.exists());
        assertEquals(filename, generatedFile.getName());
    }

    @Test
    void getFileName() {
        CommitMsgConfiguration configuration = mock(CommitMsgConfiguration.class);
        String filename = "fileName";
        when(configuration.getFileName()).thenReturn(filename);
        BashScriptCommitMsgHookGenerator generator =
                new BashScriptCommitMsgHookGenerator(configuration);
        assertEquals(filename, generator.getFileName());
    }

    @Nested
    @DisplayName("Bash script file writer")
    class BashScriptFileWriterTest {

        @TempDir
        private File parentFolder;

        @Test
        void createAndWrite_whenIOExceptionThrown_shouldThrowRuntimeIOException() {
            File nonExistentParent = new File("some-folder-name");
            assertThrows(RuntimeIOException.class,
                    () -> BashScriptCommitMsgHookGenerator.BashScriptFileWriter.createAndWrite(
                            nonExistentParent, "filename", "file content"));

        }

        @Test
        void createAndWrite_whenNullContent_shouldThrowException() {
            assertThrows(IllegalArgumentException.class,
                    () -> BashScriptCommitMsgHookGenerator.BashScriptFileWriter.createAndWrite(
                            parentFolder, "filename", null));

        }

        @Test
        void createAndWrite_whenNullFilename_shouldThrowException() {
            assertThrows(IllegalArgumentException.class,
                    () -> BashScriptCommitMsgHookGenerator.BashScriptFileWriter.createAndWrite(
                            parentFolder, null, "content"));

        }

        @Test
        void createAndWrite_whenNullParent_shouldThrowException() {
            assertThrows(IllegalArgumentException.class,
                    () -> BashScriptCommitMsgHookGenerator.BashScriptFileWriter.createAndWrite(null,
                            "filename", "content"));

        }

        @Test
        void createAndWrite_whenValidParameters_shouldReturnFileWithContent() throws IOException {
            String filename = "test-file";
            String fileContent = "file content";
            File createdFile = BashScriptCommitMsgHookGenerator.BashScriptFileWriter.createAndWrite(
                    parentFolder, filename, fileContent);
            assertTrue(createdFile.exists());
            assertEquals(fileContent, Files.readString(createdFile.toPath()));
        }

    }

    @Nested
    @DisplayName("Bash script content test")
    class BashScriptContentBuilderTest {

        private static final String FILE_WITHOUT_HEADER_MAX_LENGTH =
                "src/test/resources/git-hooks/commit-msg/withoutHeaderMaxLength";
        private static final String FILE_WITH_HEADER_MAX_LENGTH =
                "src/test/resources/git-hooks/commit-msg/withHeaderMaxLength";

        @Test
        void build_whenHasHeaderMaxLength_shouldCreateScriptContent() throws IOException {
            String expectedContent = Files.readString(Path.of(FILE_WITH_HEADER_MAX_LENGTH));
            CommitMsgConfiguration commitMsgConfiguration = mock(CommitMsgConfiguration.class);
            when(commitMsgConfiguration.getHeaderMaxLength()).thenReturn(100);
            when(commitMsgConfiguration.hasHeaderMaxLengthRestriction()).thenReturn(true);
            when(commitMsgConfiguration.getTypes()).thenReturn(
                    new TreeSet<>(List.of("fix", "test", "docs", "feat", "refactor")));
            assertEquals(expectedContent,
                    BashScriptCommitMsgHookGenerator.BashScriptContentBuilder.build(
                            commitMsgConfiguration));
        }

        @Test
        void build_whenNoHeaderMaxLength_shouldCreateScriptContent() throws IOException {
            String expectedContent = Files.readString(Path.of(FILE_WITHOUT_HEADER_MAX_LENGTH));
            CommitMsgConfiguration commitMsgConfiguration = mock(CommitMsgConfiguration.class);
            when(commitMsgConfiguration.getHeaderMaxLength()).thenReturn(
                    CommitMsgConfiguration.UNRESTRICTED_HEADER_MAX_LENGTH_VALUE);
            when(commitMsgConfiguration.hasHeaderMaxLengthRestriction()).thenReturn(false);
            when(commitMsgConfiguration.getTypes()).thenReturn(
                    new TreeSet<>(List.of("fix", "test", "docs", "feat", "refactor")));
            assertEquals(expectedContent,
                    BashScriptCommitMsgHookGenerator.BashScriptContentBuilder.build(
                            commitMsgConfiguration));
        }

        @Test
        void build_whenNullConfiguration_shouldThrowException() {
            assertThrows(IllegalArgumentException.class,
                    () -> BashScriptCommitMsgHookGenerator.BashScriptContentBuilder.build(null));
        }

    }
}