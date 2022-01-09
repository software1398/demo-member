package com.example.demomember;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findByKey(String key);
    Optional<Member> findById(String id);
    Optional<Member> findByName(String name);
    Optional<Member> findByAge(String age);
    List<Member> findAll();
}
