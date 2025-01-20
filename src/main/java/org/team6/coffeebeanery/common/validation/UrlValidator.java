package org.team6.coffeebeanery.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator implements ConstraintValidator<Url, String> {
    
    private static final String URL_REGEX =
            "^((https?|ftp)://)?[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+([\\w-./?%&=]*)?$";
    
    private Pattern pattern;
    
    @Override
    public void initialize(Url constraintAnnotation) {
        pattern = Pattern.compile(URL_REGEX);
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // null 값, 빈 문자열 허용
        }
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}