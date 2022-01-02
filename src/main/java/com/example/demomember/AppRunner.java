package com.example.demomember;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Component
public class AppRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Member member = new Member();
        member.setId("001");
        member.setName("홍길동");
        member.setAge("20");

        MemberValidator memberValidator = new MemberValidator();
        Errors errors = new BeanPropertyBindingResult(member, "member");
        memberValidator.validate(member, errors);

        errors.getAllErrors().forEach(e -> {
            log.info("============= ERROR CODES =============");
            Arrays.stream(Objects.requireNonNull(e.getCodes())).forEach(System.out::println);
            log.info("Default Messages : {}", e.getDefaultMessage());
        });


    }
}
