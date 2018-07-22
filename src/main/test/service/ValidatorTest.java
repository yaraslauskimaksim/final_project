package service;

import by.corporation.quest_fire.service.validation.Validator;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidatorTest {

    @Test
    public void isEmailValid() {
        boolean result = Validator.validateEmail("tramail.com");
        assertThat(result, is(false));
    }

    @Test
    public void isPasswordValid() {
        boolean result = Validator.validatePassword("12345");
        assertThat(result, is(false));
    }

    @Test
    public void isFieldsNotEmpty() {
        boolean result = Validator.validateEmptyFields("", "");
        assertThat(result, is(false));
    }

}