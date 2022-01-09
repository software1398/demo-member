package com.example.demomember;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
public class MemberValidator implements Validator {
    @Autowired
    MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final String numberRegex = "^[0-9]+$";
        Member member = (Member) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "field.required", "ID를 입력하세요.\n");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "filed.required", "이름을 입력하세요.\n");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "field.required", "나이를 입력하세요.\n");

        if(!member.getAge().matches(numberRegex)) errors.rejectValue("age", "field.number", "나이는 숫자만 가능합니다.\n");
        if(Integer.parseInt(member.getAge()) < 14) errors.rejectValue("age", "field.limited", "14세 이상만 가입할 수 있습니다.\n");
    }
}
