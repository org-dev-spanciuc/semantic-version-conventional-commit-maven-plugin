package org.dev.spanciuc.maven.mojo.githooks.utils;

/**
 * Utility class for parameters sanitization.
 */
public class ParameterSanitizer {

    /**
     * To prevent instance creation.
     */
    private ParameterSanitizer() {
    }

    /**
     * Sanitizes a required string parameter.
     * <p>
     * Checks if value is not null and does not contain only white spaces. If so, removes leading
     * and trailing white spaces and return the value.
     *
     * @param value     value to sanitize.
     * @param valueName the parameter name.
     * @return sanitized value.
     * @throws IllegalArgumentException if the value is null.
     * @throws IllegalArgumentException if the value contains only white spaces.
     */
    public static String sanitizeRequiredStringParameter(String value, String valueName) {
        if (null == value) {
            throw new IllegalArgumentException(
                    String.format(Messages.MESSAGE_VALUE_MUST_NOT_BE_NULL, valueName));
        }
        String result = value.strip();
        if (result.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format(Messages.MESSAGE_VALUE_MUST_NOT_BE_EMPTY, valueName));
        }
        return result;
    }
}
