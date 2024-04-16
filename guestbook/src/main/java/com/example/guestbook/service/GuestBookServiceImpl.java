package com.example.guestbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.dto.PageRequestDto;
import com.example.guestbook.dto.PageResultDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.entity.QGuestBook;
import com.example.guestbook.repository.GuestBookRepository;
import com.querydsl.core.BooleanBuilder;

@Service
public class GuestBookServiceImpl implements GuestBookService {

    @Autowired // 이거 안쓰면 injection 오류 NullPointerException 초기화가 안되서 발생
    GuestBookRepository guestBookRepository;

    // @Override
    // public List<GuestBookDto> getList() {
    // List<GuestBook> list =
    // guestBookRepository.findAll(Sort.by("gno").descending());

    // Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));
    // return list.stream().map(fn).collect(Collectors.toList()); // 좀 더 세련된 방법

    // // List<GuestBookDto> dto = new ArrayList<>();
    // // for (GuestBook guestBook : list) {
    // // dto.add(entityToDto(guestBook));
    // // }
    // // return dto;
    // }

    @Override
    public GuestBookDto getRow(Long gno) {
        GuestBook guestBook = guestBookRepository.findById(gno).get();
        return entityToDto(guestBook);
    }

    @Override
    public void modify(GuestBookDto guestBookDto) {
        GuestBook guestBook = guestBookRepository.findById(guestBookDto.getGno()).get();
        guestBook.setTitle(guestBookDto.getTitle());
        guestBook.setContent(guestBookDto.getContent());
        guestBook.setWriter(guestBookDto.getWriter());
        guestBookRepository.save(guestBook);
    }

    @Override
    public void delete(Long gno) {
        guestBookRepository.deleteById(gno);
    }

    @Override
    public PageResultDto<GuestBookDto, GuestBook> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("gno").descending());
        // Page<GuestBook> result = guestBookRepository.findAll(pageable);

        BooleanBuilder builder = getSearch(requestDto);
        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable); // Q클래스에 있는 findAll

        Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<GuestBookDto, GuestBook>(result, fn);
    }

    @Override
    public Long create(GuestBookDto guestBookDto) {
        return guestBookRepository.save(dtoToEntity(guestBookDto)).getGno();
    }

    // book 프로젝트에서는 bookRepository 에 작성함
    private BooleanBuilder getSearch(PageRequestDto requestDto) {
        BooleanBuilder builder = new BooleanBuilder();

        // Q 클래스 사용 - entity 토대로 만들어진 Q클래스불러오기
        QGuestBook guestBook = QGuestBook.guestBook;

        // type, keyword 가져오기
        String type = requestDto.getType();
        String keyword = requestDto.getKeyword();

        // gno > 0 (sql 구문 최적화를 위해서 추가 / where절에 pk 를 추가해서 테이블 찾는걸 좀 더 삐르게함)
        builder.and(guestBook.gno.gt(0L)); // gt = greaterThan

        if (type == null || type.trim().length() == 0) { // type 이 비어있는지 확인
            return builder;
        }

        // 검색 타입이 있는 경우 / 다 포함할 수도 있으니 else를 쓰지 않음
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(guestBook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(guestBook.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(guestBook.writer.contains(keyword));
        }
        builder.and(conditionBuilder); // where gno > 0 and (title like ? or content like ? or writer like ?);

        return builder;
    }

}
