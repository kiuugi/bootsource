package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Memo;

// JUnit : 테스트 라이브러리

@SpringBootTest // 테스트 전용 클래스
public class MemoRepositoryTest {
    @Autowired // MemoRepository memoRepository = new MemoRepositoryImpl();
    private MemoRepository memoRepository;

    @Test // 테스트 메소드(리턴타입은 void)
    public void insertMemo() {
        // 100 개의 메모 입력

        for (int i = 1; i < 101; i++) {
            Memo memo = new Memo();
            memo.setMemoText("MemoText" + i);
            // .save() : insert, update
            memoRepository.save(memo); // == dao.insert()
        }

    }

    @Test
    public void getMemo() {
        // 특정 아이디 메모 조화
        // Optional : null을 체크할수 있는 메소드 보유
        // findById : select m1_0.mno, m1_0.memo_text
        // from memotbl m1_0
        // where m1_0.mno=?
        Optional<Memo> result = memoRepository.findById(21L);

        System.out.println(result.get());
    }

    @Test
    public void getMemoList() {
        List<Memo> result = memoRepository.findAll();

        for (Memo memo : result) {
            System.out.println(memo);

        }
    }

    @Test
    public void updateMemo() {
        // 업데이트 할 entity 찾기
        Optional<Memo> result = memoRepository.findById(15L);
        Memo memo = result.get();

        memo.setMemoText("update Title..//");
        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void deleteMemo() {
        // 삭제할 entity 찾기
        Optional<Memo> result = memoRepository.findById(24L);
        Memo memo = result.get();
        memoRepository.delete(memo);

        // 삭제
        System.out.println("삭제됨" + memoRepository.findById(24L));
        // 삭제됨Optional.empty
    }

    @Test
    public void jpqlTest() {

        List<Memo> list = memoRepository.findByMnoLessThan(10L);
        list.forEach(System.out::println);
        list = memoRepository.findByMnoLessThanOrderByMnoDesc(20L);
        list.forEach(System.out::println);
        list = memoRepository.findByMnoBetween(30L, 50L);
        list.forEach(System.out::println);

    }
}
