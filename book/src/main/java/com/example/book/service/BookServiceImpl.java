package com.example.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDto;
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

    @Override
    public List<BookDto> getList() {
        List<Book> books = bookRepository.findAll(Sort.by("id").descending());

        // List<BookDto> bookDtos = new ArrayList<>();
        // books.forEach(book -> bookDtos.add(entityToDto(book)));

        // List 처리할 때 stream 이라는 좋은것이 있으니 활용하도록하자
        List<BookDto> bookDtos = books.stream().map(book -> entityToDto(book)).collect(Collectors.toList());

        return bookDtos;
    }

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
    public BookDto getRow(Long id) {
        Book book = bookRepository.findById(id).get();
        return entityToDto(book);
    }

    @Override
    public Long update(BookDto updateDto) {
        Category category = categoryRepository.findByName(updateDto.getCategoryName()).get();
        Publisher publisher = publisherRepository.findByName(updateDto.getPublisherName()).get();

        Book entity = bookRepository.findById(updateDto.getId()).get();
        entity = Book.builder()
                .title(updateDto.getTitle())
                .writer(updateDto.getWriter())
                .price(updateDto.getPrice())
                .salePrice(updateDto.getSalePrice())
                .category(category)
                .publisher(publisher)
                .build();
        Book updateBook = bookRepository.save(entity);

        return updateBook.getId();
    }

    @Override
    public void bookDelete(Long id) {
        bookRepository.deleteById(id);
    }

}
