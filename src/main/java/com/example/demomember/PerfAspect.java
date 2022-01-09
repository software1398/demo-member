package com.example.demomember;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@Component
@Aspect
public class PerfAspect {

    @Autowired
    ResourceLoader resourceLoader;

    @Around("execution(* com.example.demomember.event..*.*(..)))")
    public Objects logPerf(ProceedingJoinPoint pjp) throws Throwable{
        String logFilePath = System.getProperty("user.home") + "\\Downloads\\logfile.txt";
        Resource fileProtocolResource = resourceLoader.getResource("file:///" + logFilePath);
        if(!fileProtocolResource.exists()){
            Path filePath = Paths.get(logFilePath);
            try {
                Files.createFile(filePath);
                System.out.println("logfile을 생성하였습니다.");
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        String target = pjp.getTarget().toString();

        BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true));
        if (target.indexOf("EventHandler") > 0) {
            String memberObj = pjp.getArgs()[0].toString();
            String id = memberObj.substring(memberObj.indexOf("id") + 3, memberObj.indexOf("name") - 2);
            writer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                    " " + id + "님이 가입했습니다.\n");
        }else {
            writer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                    " 관리자가 전체 메시지를 보냈습니다.\n");
        }
        writer.close();
        return (Objects) pjp.proceed();
    }
}
