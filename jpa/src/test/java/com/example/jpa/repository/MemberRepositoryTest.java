package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Member;
import com.example.jpa.entity.RoleType;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertTest() {
        IntStream.range(1, 11).forEach(i -> {
            Member member = Member.builder()
                    .id("id" + i)
                    .userName("이춘향이오" + i)
                    .age((int) (30 + i))
                    .roleType(RoleType.ADMIN)
                    .description("이건뭐하는건데")
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void readTest() {
        System.out.println(memberRepository.findById("id3"));
        System.out.println("--------");
        memberRepository.findAll().forEach(member -> {
            System.out.println(member);
        });
        // 특정 이름 조회 // repository에 메소드 하나 만듬
        memberRepository.findByUserName("이춘향이오3").forEach(member -> {
            System.out.println(member);
        });
    }

    @Test
    public void updateTest() {
        Member member = memberRepository.findById("id5").get();
        // Optional<Member> result = memberRepository.findById("id5");
        // result.ifPresent(member ->{
        // member.setRoleType(RoleType.ADMIN)
        // memberRepository.save(member)
        // });
        member.setAge(25);
        member.setRoleType(RoleType.USER);
        System.out.println(memberRepository.save(member));
    }

    @Test
    public void deleteTest() {
        Member member = memberRepository.findById("id10").get();
        memberRepository.delete(member);
    }
}
