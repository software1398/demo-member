package com.example.demomember;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Slf4j
public class MemberValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final String numberRegex = "^[0-9]+$";
        Member member = (Member) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "filed.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "field.required");

        if(!member.getAge().matches(numberRegex)) {
            log.error("Member Age Property Not number");
            errors.rejectValue("age", "field.number");
        }
    }
}
