package com.example.demomember.event;

import com.example.demomember.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventHandler {

    @EventListener
    @Async
    public void Handle(Member member){
        System.out.println("========= 가입 메시지 =========");
        System.out.printf("%s님, 가입을 환영합니다.\n", member.getName());
        System.out.println("=============================");
    }
}
