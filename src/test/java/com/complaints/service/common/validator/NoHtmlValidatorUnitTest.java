package com.complaints.service.common.validator;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class NoHtmlValidatorUnitTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    record TestDto(@NoHtml String text) {
    }

    @ParameterizedTest(name = "''{0}'' shouldBe {1}")
    @CsvSource({
            "'Hello world',             true",
            "'<script>alert(1)</script>', false",
            "'normal text',             true",
            "'<b>bold</b>',             false",
            "'2 < 3',                   false",
            "'no brackets here',        true",
            "'',                        true",
            "NULL,                      true"
    })
    void testNoHtmlValidator(String input, boolean expectedValid) {
        assertEquals(expectedValid, validator.validate(new TestDto(input)).isEmpty());
    }
}
