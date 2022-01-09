package com.example.demomember.event;

import com.example.demomember.Member;
import com.example.demomember.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SendMessage {
    @Autowired
    MemberRepository memberRepository;
    public void sendMessage(String msg) {
        List<Member> all = memberRepository.findAll();
        all.forEach(m -> System.out.printf("%s님이 메시지를 수신하였습니다. %s\n", m.getName(), msg));
    }

}

