package com.example.jpa.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Item;
import com.example.jpa.entity.ItemSellStatus;

@SpringBootTest
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void createTest() {
        IntStream.range(1, 11).forEach(i -> { // IntStream, LongStream 밑에 쓰이는 변수 타입 맞춰서 쓰기
            Item item = Item.builder()
                    // .id(i) 시퀀스가 없다면 이렇게 넣어주면 됨
                    .itemNm("운동화" + i)
                    .price(30000 * i + i)
                    .stockNumber(30)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .build();
            itemRepository.save(item);
        });
    }

    @Test
    public void readTest() {
        System.out.println(itemRepository.findById(8L));
        System.out.println("----------");
        itemRepository.findAll().forEach(item -> System.out.println(item));
    }

    @Test
    public void updateTest() {
        // entity 찾기
        Item result = itemRepository.findById(5L).get();
        // 수정 - 아이템명, 가격
        result.setPrice(35000);
        result.setItemNm("N운동화");
        System.out.println(itemRepository.save(result));
    }

    @Test
    public void deleteTest() {
        Item result = itemRepository.findById(2L).get();
        itemRepository.delete(result);
        // 확인
        System.out.println(itemRepository.findById(2L));
    }
}
