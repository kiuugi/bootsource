package com.example.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.entity.Category;
import com.example.book.entity.Publisher;
import com.example.book.repository.BookRepository;
import com.example.book.repository.CategoryRepository;
import com.example.book.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;

    // @Override
    // public List<BookDto> getList() {
    // List<Book> books = bookRepository.findAll(Sort.by("id").descending());

    // // List<BookDto> bookDtos = new ArrayList<>();
    // // books.forEach(book -> bookDtos.add(entityToDto(book)));

    // // List 처리할 때 stream 이라는 좋은것이 있으니 활용하도록하자
    // List<BookDto> bookDtos = books.stream().map(book ->
    // entityToDto(book)).collect(Collectors.toList());

    // return bookDtos;
    // }

    @Override
    public Long bookCreate(BookDto dto) {
        Category category = categoryRepository.findByName(dto.getCategoryName()).get();
        Publisher publisher = publisherRepository.findByName(dto.getPublisherName()).get();

        Book book = dtoToEntity(dto);
        book.setCategory(category);
        book.setPublisher(publisher);

        Book newBook = bookRepository.save(book);

        return newBook.getId();
    }

    @Override
    public List<String> categoryNameList() {
        List<Category> list = categoryRepository.findAll();
        return list.stream().map(entity -> entity.getName()).collect(Collectors.toList());
    }

    @Override
    public List<String> publisherNameList() {
        List<Publisher> list = publisherRepository.findAll();
        return list.stream().map(entity -> entity.getName()).collect(Collectors.toList());
    }

    @Override
    public BookDto getRow(Long id) {
        Book book = bookRepository.findById(id).get();
        return entityToDto(book);
    }

    @Override
    public Long update(BookDto updateDto) {

        Book entity = bookRepository.findById(updateDto.getId()).get();
        entity.setPrice(updateDto.getPrice());
        entity.setSalePrice(updateDto.getSalePrice());
        entity.setTitle(updateDto.getTitle());
        entity.setWriter(updateDto.getWriter());
        Book updateBook = bookRepository.save(entity);

        return updateBook.getId();
    }

    @Override
    public void bookDelete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public PageResultDto<BookDto, Book> getList(PageRequestDto requestDto) {
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        // PageRequest.of(0, 10, Sort.by("id").descending());

        // Page : 페이지 나누기에 필요한 메소드 제공
        // ==> PageDto와 같은 역할(model2 에서 했던거)
        Page<Book> result = bookRepository
                .findAll(bookRepository.makePredicate(requestDto.getType(), requestDto.getKeyword()), pageable);
        Function<Book, BookDto> fn = (entity -> entityToDto(entity));
        return new PageResultDto<>(result, fn);
    }

}
