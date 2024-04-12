package com.example.mart.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.QItem;
import com.example.mart.entity.QMember;
import com.example.mart.entity.QOrder;
import com.example.mart.entity.QOrderItem;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;

public class QueryDslOrderRepositoryImpl extends QuerydslRepositorySupport implements QueryDslOrderRepository {

    public QueryDslOrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Object[]> joinList() {
        // Q객체 가져오기 (taget 에 pomx.xml 이 자동 생성한 Q객체들)
        QOrder order = QOrder.order;
        QMember member = QMember.member;
        QOrderItem orderItem = QOrderItem.orderItem;

        // 쿼리문 생성 1. JPAQuery, 2. JPQLQuery
        // select * from order o join member m on o.member_id = m.member_id <= 원래 sql 구문
        JPQLQuery<Order> query = from(order);
        // join L innerJoin, leftJoin, rightJoin, fullJoin 지원
        // join(조인대상, 별칭으로 사용할 쿼리 타입)
        // query.innerJoin(order.member, member); // on o.member_id = m.member_id

        // 내부조인 : join(), innerJoin() 둘다 가능
        query.join(order.member, member)
                .leftJoin(order.orderItems, orderItem);
        // select m, t
        JPQLQuery<Tuple> tuple = query.select(order, member, orderItem);
        List<Tuple> result = tuple.fetch();
        System.out.println("결과");
        // [[order(), member()]], ... <-요거 하나가 tuple
        System.out.println(result);

        // List<Tuple> ==> List<Object[]>
        List<Object[]> list = result.stream().map(t -> t.toArray()).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Member> members() {
        QMember member = QMember.member;
        // select * from member where name = 'user1' order by name desc;
        JPQLQuery<Member> query = from(member);
        query.where(member.name.eq("user1")).orderBy(member.name.desc());
        JPQLQuery<Member> tuple = query.select(member);
        List<Member> list = tuple.fetch();
        return list;
    }

    @Override
    public List<Item> items() {
        // select * from item where name='바지' and price > 20000

        QItem item = QItem.item;
        JPQLQuery<Item> query = from(item).where(item.name.eq("바지").and(item.price.gt(20000)));
        JPQLQuery<Item> tuple = query.select(item);
        List<Item> list = tuple.fetch();
        return list;
    }

}
