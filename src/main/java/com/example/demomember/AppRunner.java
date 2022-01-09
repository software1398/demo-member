package com.example.demomember;

import com.example.demomember.event.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

@Slf4j
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MessageSource messageSource;
    @Autowired
    Validator validator;
    @Autowired
    ApplicationEventPublisher publisher;
    @Autowired
    SendMessage sendMessage;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        MemberValidator memberValidator = new MemberValidator();

        while (true) {
            Member member = new Member();
            System.out.print("[1] : 회원가입\n[2] : 전체 메시지 보내기\n[3] : 종료\n");
            String input = br.readLine();
            if (input.equals("1")) {
                System.out.print(messageSource.getMessage("id.blank", new String[]{}, Locale.KOREA) + "\t");
                member.setId(br.readLine().trim());
                System.out.print(messageSource.getMessage("name.blank", new String[]{}, Locale.KOREA) + "\t");
                member.setName(br.readLine().trim());
                System.out.print(messageSource.getMessage("age.blank", new String[]{}, Locale.KOREA) + "\t");
                member.setAge(br.readLine().trim());

                Errors errors = new BeanPropertyBindingResult(member, "member");
                memberValidator.validate(member, errors);
                errors.getAllErrors().forEach(e -> {
                    System.out.print("======= ERRCODE : ");
                    System.out.print(e.getDefaultMessage());
                });

                if(!errors.hasErrors()){
                    if(memberRepository.findById(member.getId()).isPresent()){
                        System.out.print(messageSource.getMessage("id.duplication", new String[]{}, Locale.KOREA) + "\n");
                    }else{
                        memberRepository.save(member);
                        publisher.publishEvent(member);
                    }
                }
            }else if (input.equals("2")){
                System.out.print(messageSource.getMessage("msg.notify", new String[]{}, Locale.KOREA) + "\t");
                String msg = br.readLine().trim();
                sendMessage.sendMessage(msg);
            }else if (input.equals("3")) {
                System.exit(0);
            }
        }
    }
}
