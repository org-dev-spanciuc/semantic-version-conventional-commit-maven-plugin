package org.dev.spanciuc.maven.mojo.githooks.commit_msg;

import org.dev.spanciuc.maven.mojo.githooks.GitHookFileGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class CommitMsgConfigurationTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "      "})
    void constructor_whenInvalidFileName_shouldThrowIllegalArgument(String fileName) {
        CommitMsgParameters commitMsgParameters = spy(buildValidCommitMessageParameters());
        when(commitMsgParameters.getFileName()).thenReturn(fileName);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommitMsgConfiguration(commitMsgParameters));
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -2, 0})
    void constructor_whenInvalidHeaderMaxLength_shouldThrowIllegalArgument(int headerMaxLength) {
        CommitMsgParameters commitMsgParameters = spy(buildValidCommitMessageParameters());
        when(commitMsgParameters.getHeaderMaxLength()).thenReturn(headerMaxLength);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommitMsgConfiguration(commitMsgParameters));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @MethodSource("invalidTypesProvider")
    void constructor_whenInvalidTypes_shouldThrowIllegalArgument(String types) {
        CommitMsgParameters commitMsgParameters = spy(buildValidCommitMessageParameters());
        when(commitMsgParameters.getTypes()).thenReturn(types);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommitMsgConfiguration(commitMsgParameters));
    }

    @Test
    void constructor_whenNullCommitMsgParameter_shouldThrowIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new CommitMsgConfiguration(null));
    }

    @Test
    void constructor_whenValidParameters_shouldCreateConfigurationInstance() {
        String fileName = "  some file name  ";
        boolean isEnabled = false;
        int headerMaxLength = -1;
        String types = "t-1, some type 2,    1234    ";

        CommitMsgParameters parameters =
                new CommitMsgParameters(isEnabled, fileName, headerMaxLength, types);

        CommitMsgConfiguration configuration = new CommitMsgConfiguration(parameters);

        assertEquals(fileName.strip(), configuration.getFileName());
        assertEquals(isEnabled, configuration.isEnabled());
        assertEquals(headerMaxLength, configuration.getHeaderMaxLength());
        assertEquals(Arrays.stream(types.split(CommitMsgConfiguration.TYPES_SPLIT_CHAR))
                .map(String::strip)
                .collect(Collectors.toSet()), configuration.getTypes());
    }

    @Test
    void getGitHookFileGenerator_shouldReturnBashScriptCommitMsgHookGenerator() {
        CommitMsgParameters commitMsgParameters = buildValidCommitMessageParameters();
        CommitMsgConfiguration configuration = new CommitMsgConfiguration(commitMsgParameters);
        GitHookFileGenerator generator = configuration.getGitHookFileGenerator();
        assertNotNull(generator);
        assertInstanceOf(BashScriptCommitMsgHookGenerator.class, generator);
    }

    @Test
    void getTypes_shouldReturnUnmodifiableSortedSet() {
        CommitMsgParameters commitMsgParameters = spy(buildValidCommitMessageParameters());
        when(commitMsgParameters.getTypes()).thenReturn("3, 2, 1");
        CommitMsgConfiguration configuration = new CommitMsgConfiguration(commitMsgParameters);
        Set<String> types = configuration.getTypes();
        assertEquals(new TreeSet<>(Arrays.asList("1", "2", "3")), types);
        assertThrows(UnsupportedOperationException.class, () -> types.add(null));
        assertThrows(UnsupportedOperationException.class, types::clear);
    }

    @Test
    void hasHeaderMaxLengthRestriction_whenHasNotRestriction_shouldReturnFalse() {
        CommitMsgParameters commitMsgParameters = spy(buildValidCommitMessageParameters());
        when(commitMsgParameters.getHeaderMaxLength()).thenReturn(
                CommitMsgConfiguration.UNRESTRICTED_HEADER_MAX_LENGTH_VALUE);
        CommitMsgConfiguration configuration = new CommitMsgConfiguration(commitMsgParameters);
        assertFalse(configuration.hasHeaderMaxLengthRestriction());
    }

    @Test
    void hasHeaderMaxLengthRestriction_whenHasRestriction_shouldReturnTrue() {
        CommitMsgParameters commitMsgParameters = spy(buildValidCommitMessageParameters());
        when(commitMsgParameters.getHeaderMaxLength()).thenReturn(100);
        CommitMsgConfiguration configuration = new CommitMsgConfiguration(commitMsgParameters);
        assertTrue(configuration.hasHeaderMaxLengthRestriction());
    }

    private CommitMsgParameters buildValidCommitMessageParameters() {
        return new CommitMsgParameters(true, "file name", 100, "type 1, type 2");
    }

    static Stream<Arguments> invalidTypesProvider() {
        return Stream.of(Arguments.of(" some type  ,   type-1  , "),
                Arguments.of("some type, type-1,    "), Arguments.of("type-1, some type, type-1"));
    }
}