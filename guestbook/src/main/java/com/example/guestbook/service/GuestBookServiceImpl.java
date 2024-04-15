package com.example.guestbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.guestbook.dto.GuestBookDto;
import com.example.guestbook.entity.GuestBook;
import com.example.guestbook.repository.GuestBookRepository;

@Service
public class GuestBookServiceImpl implements GuestBookService {

    @Autowired // 이거 안쓰면 injection 오류 NullPointerException 초기화가 안되서 발생
    GuestBookRepository guestBookRepository;

    @Override
    public List<GuestBookDto> getList() {
        List<GuestBook> list = guestBookRepository.findAll(Sort.by("gno").descending());

        Function<GuestBook, GuestBookDto> fn = (entity -> entityToDto(entity));
        return list.stream().map(fn).collect(Collectors.toList()); // 좀 더 세련된 방법

        // List<GuestBookDto> dto = new ArrayList<>();
        // for (GuestBook guestBook : list) {
        // dto.add(entityToDto(guestBook));
        // }
        // return dto;
    }

}
