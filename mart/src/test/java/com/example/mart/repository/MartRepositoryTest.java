package com.example.mart.repository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mart.entity.Delivery;
import com.example.mart.entity.DeliveryStatus;
import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.OrderStatus;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MartRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Test
    public void insertTest() {
        // 잠시 데이터가 필요해서 생성
        // 멤버3
        memberRepository.save(Member.builder()
                .name("user1")
                .zipcode("1234")
                .city("서울시")
                .street("종로")
                .build());
        memberRepository.save(Member.builder()
                .name("user2")
                .zipcode("1235")
                .city("고양시")
                .street("일산")
                .build());
        memberRepository.save(Member.builder()
                .name("user3")
                .zipcode("1236")
                .city("부산")
                .street("종로")
                .build());
        // 아이템3
        itemRepository.save(Item.builder()
                .name("티셔츠")
                .price(20000)
                .stockQuantity(300)
                .build());
        itemRepository.save(Item.builder()
                .name("운동화")
                .price(50000)
                .stockQuantity(130)
                .build());
        itemRepository.save(Item.builder()
                .name("바지")
                .price(40000)
                .stockQuantity(200)
                .build());
    }

    @Test
    public void orderInsertTest() { //
        // 누가 주문 하느냐
        Member member = Member.builder().id(1L).build();
        // 어떤 아이템
        Item item = Item.builder().id(1L).build();
        // 주문 + 주문상품
        Order order = Order.builder().member(member).orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER).build();
        orderRepository.save(order);
        OrderItem orderItem = OrderItem.builder().item(item).order(order).orderPrice(20000).count(2).build();
        orderItemRepository.save(orderItem);

    }

    @Test
    public void readTest() {
        // 전체 회원 조회
        memberRepository.findAll().forEach(m -> System.out.println(m));
        // 전체 아이템 조회
        itemRepository.findAll().forEach(i -> System.out.println(i));

        // 주문아이템 조회 시 주문 정보 확인 / orderItem에 사실상 모든 정보다 담겨있음
        // OrderItem(id=1, item=Item(id=1, name=티셔츠, price=20000, stockQuantity=300),
        // order=Order(id=1, member=Member(id=1, name=user1, zipcode=1234, city=서울시,
        // street=종로), orderDate=2024-04-05T15:04:33.651713, orderStatus=ORDER),
        // orderPrice=20000, count=2)
        orderItemRepository.findAll().forEach(entity -> {
            System.out.println(entity);
            System.out.println("상품정보 : " + entity.getItem());
            System.out.println("구매자 : " + entity.getOrder().getMember().getName());
            // 객체 그래프 탐색 ( 그냥 . 찍어서 들어가는거 ) => N : 1 관계에서 FetchType.EAGER 이기 때문에 가능
            // => N : 1 의 FetchType.EAGER 일때 그냥 . 찍어서 들어갈 수 있다.
        });
    }

    @Transactional
    @Test
    public void readTest2() {
        // @OneToMany 를 이용해 조회
        // 관련있는 엔티티를 안 가지고 옴
        // Order : OrderItem
        // Order를 기준으로 OrderItem을 조회
        Order order = orderRepository.findById(7L).get();
        System.out.println(order);

        // Order 를 기준으로 OrderItem 조회
        // 1. @Transactional, 2. FetchType 변경
        System.out.println(order.getOrderItems());
        // 배송지 조회
        System.out.println(order.getDelivery().getCity());
    }

    @Transactional
    @Test
    public void readTest3() {
        // @OneToMany 를 이용해 조회
        // Member : Order
        // 회원의 주문 내역 조회
        Member member = memberRepository.findById(1L).get();
        System.out.println(member);

        // Order 를 기준으로 OrderItem 조회
        // 1. @Transactional, 2. FetchType 변경
        System.out.println(member.getOrders());
    }

    @Test
    public void updateTest() {
        // 멤버 주소 수정
        Member member = memberRepository.findById(3L).get();
        member.setCity("서울시");
        memberRepository.save(member);

        // 아이템 가격 수정
        Item item = itemRepository.findById(2L).get();
        item.setPrice(85000);
        itemRepository.save(item);

        // 주문상대 수정 ORDER -> CANCEL
        Order order = orderRepository.findById(1L).get();
        order.setOrderStatus(OrderStatus.CANCEL);
        orderRepository.save(order);
    }

    @Test
    public void deleteTest() {
        // 주문 제거 => 주문아이템도 같이 제거

        // orderItemRepository.deleteById(2L);
        // orderRepository.deleteById(2L); // => 단독으로 하면 주문아이템이 존재하기 때문에 에러가 남

        // 주문 아이템 제거 후 주문 제거
        orderItemRepository.delete(OrderItem.builder().id(3L).build());
        orderRepository.deleteById(3L); // 위에꺼랑 똑같은거임
    }

    @Test
    public void orderInsertDeliveryTest() { //
        // 누가 주문 하느냐
        Member member = Member.builder().id(1L).build();
        // 어떤 아이템
        Item item = Item.builder().id(1L).build();
        // 배송지
        Delivery delivery = Delivery.builder().city("서울시").street("123-12").zipcode("11160")
                .deliveryStatus(DeliveryStatus.READY).build();
        deliveryRepository.save(delivery);
        // 주문 + 주문상품
        Order order = Order.builder().member(member).orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER).delivery(delivery).build();
        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder().item(item).order(order).orderPrice(20000).count(2).build();
        orderItemRepository.save(orderItem);
    }

    @Test
    public void delivertOrderGet() {
        // 배송지를 통해서 관련있는 Order 가져오기
        Delivery delivery = deliveryRepository.findById(1L).get();
        System.out.println(delivery);
        System.out.println("관련 주문" + delivery.getOrder());

    }
}
