package com.complaints.service.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Simple validator to check if a string contains HTML tags.
 * Other way is to sanitize the input using a library.
 */
public class NoHtmlValidator implements ConstraintValidator<NoHtml, String> {

    private static final Pattern HTML_PATTERN = Pattern.compile(".*[<>].*");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || !HTML_PATTERN.matcher(value).matches();
    }
}
