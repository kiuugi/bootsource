package com.example.jpa.repository;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Locker;
import com.example.jpa.entity.SportsMember;

@SpringBootTest
public class LockerRepositoryTest {

    @Autowired
    private SportsMemberRepository sportsMemberRepository;

    @Autowired
    private LockerRepository lockerRepository;

    @Test
    public void insertTest() {
        // Locker 삽입
        LongStream.rangeClosed(1, 3).forEach(i -> {
            Locker locker = Locker.builder().name("locker" + i).build();
            lockerRepository.save(locker);
        });
        LongStream.rangeClosed(4, 7).forEach(i -> {
            SportsMember sportsMember = SportsMember.builder().name("user" + i).locker(Locker.builder().id(i).build())
                    .build();
            sportsMemberRepository.save(sportsMember);
        });
    }

    @Test
    public void updateTest() {
        SportsMember sportsMember = sportsMemberRepository.findById(9L).get();
        sportsMember.setName("이춘향");
        sportsMemberRepository.save(sportsMember);
    }

    @Test
    public void readTest() {
        // 회원 조회 후 락커정보조회
        SportsMember sportsMember = sportsMemberRepository.findById(1L).get();
        System.out.println("스포츠멤버 정보 : " + sportsMemberRepository.findById(1L).get());

        System.out.println("라커 정보 : " + sportsMember.getLocker());
        System.out.println("라커 id : " + sportsMember.getLocker().getId());
    }

    @Test
    public void readTest2() {
        // 락커정보조회 후 회원 조회
        Locker locker = lockerRepository.findById(1L).get();
        System.out.println(locker);
        System.out.println("락커를 쓰고있는 멤버정보" + locker.getSportsMember());
        System.out.println("락커를 쓰고있는 멤버 이름" + locker.getSportsMember().getName());
    }

}
