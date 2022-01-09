package com.example.demomember;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static final Map<String, Member> store = new HashMap<>();
    private static long key = 0L;

    @Override
    public Member save(Member member) {
        store.put(String.valueOf(++key), member);
        return member;
    }

    @Override
    public Optional<Member> findByKey(String key) {
        return Optional.ofNullable(store.get(key));
    }

    @Override
    public Optional<Member> findById(String id) {
        return store.values().stream()
                .filter(member -> Objects.equals(member.getId(), id))
                .findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> Objects.equals(member.getName(), name))
                .findAny();
    }

    @Override
    public Optional<Member> findByAge(String age) {
        return store.values().stream()
                .filter(member -> Objects.equals(member.getAge(), age))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
