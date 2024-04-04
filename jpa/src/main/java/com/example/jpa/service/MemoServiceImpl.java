package com.example.jpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.jpa.dto.MemoDto;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // @NotNull, final 이 붙은 멤버변수를 대상으로 생성자 생성
public class MemoServiceImpl {

    private final MemoRepository memoRepository; // final을 때고 @AutoWired 써도 됨

    public List<MemoDto> getMemoList() {
        List<Memo> entites = memoRepository.findAll();

        List<MemoDto> list = new ArrayList<>();
        entites.forEach(entity -> list.add(entityToDto(entity)));
        return list;
    }

    public MemoDto getMemo(Long mno) {
        Memo entites = memoRepository.findById(mno).get();

        return entityToDto(entites);
    }

    public MemoDto updateMemo(MemoDto mDto) {
        // 업데이트 대상 찾기
        Memo entity = memoRepository.findById(mDto.getMno()).get();
        // 업데이트 할 내용 바꿔주기
        entity.setMemoText(mDto.getMemoText());
        // 업데이트 실행
        return entityToDto(memoRepository.save(entity));
    }

    public void deleteMemo(Long mno) {
        // Memo entity = memoRepository.findById(mno).get();
        // memoRepository.delete(entity);

        memoRepository.deleteById(mno);
    }

    public void insertMemo(MemoDto mDto) {
        // dto ==> entity 로 바꾸기
        memoRepository.save(dtoToEntity(mDto));

    }

    // 받은 entity를 memoDto로 변환
    private MemoDto entityToDto(Memo entity) {
        MemoDto mDto = MemoDto.builder()
                .mno(entity.getMno())
                .memoText(entity.getMemoText())
                .build();

        return mDto;
    }

    private Memo dtoToEntity(MemoDto mDto) {
        Memo entity = Memo.builder()
                .mno(mDto.getMno())
                .memoText(mDto.getMemoText())
                .build();

        return entity;
    }

}