package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.entity.Child;
import com.example.jpa.entity.Parent;

@SpringBootTest
public class ParentRepositoryTest {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    @Test
    public void insertTest() {
        // 부모 한명에 자식 2명
        Parent parent = Parent.builder().name("pName1").build();
        parentRepository.save(parent);

        Child child1 = Child.builder().name("cName1").parent(parent).build();
        Child child2 = Child.builder().name("cName2").parent(parent).build();
        childRepository.save(child1);
        childRepository.save(child2);
    }

    @Test
    public void insertCascadeTest() {
        // 부모 한명에 자식 2명
        Parent parent = Parent.builder().name("pName3").build();

        Child child1 = Child.builder().name("cName1").parent(parent).build();
        Child child2 = Child.builder().name("cName2").parent(parent).build();
        parent.getChildList().add(child1);
        parent.getChildList().add(child2); // save를 해주는게 아니라 DB에 insert 작업은 이루어지지않음

        parentRepository.save(parent); // cascade 를 하면 부모에 저장되는 자식값이 저장됨
        // cascade : 영속상태 전이
        // 부모가 영속 상태 시 자식도 같이 영속상태로 감

        // childRepository.save(child1);
        // childRepository.save(child2);InvalidDataAccessApiUsageException
    }

    @Test
    public void delete1CascadeTest() {
        // 부모가 자식을 가지고 있는경우 삭제 시 자식의 부모 아이디 변경 후 부모 삭제 / 자식이 있는 한 부모 삭제 불가능
        Parent parent = Parent.builder().id(1L).build();

        // 부모를 null 지정
        // Child child1 = Child.builder().id(1L).parent(null).build();
        // Child child2 = Child.builder().id(2L).parent(null).build();
        Child child1 = Child.builder().id(1L).build();
        childRepository.delete(child1);
        Child child2 = Child.builder().id(2L).build();
        childRepository.delete(child2);

        parentRepository.delete(parent);

    }

    @Test
    public void delete2CascadeTest() {
        // 부모 한명에 자식 2명
        Parent parent = Parent.builder().id(52L).build();

        Child child1 = Child.builder().id(102L).build();
        parent.getChildList().add(child1);
        Child child2 = Child.builder().id(103L).build();
        parent.getChildList().add(child2);

        parentRepository.delete(parent);

    }

    // @Transactional
    @Test
    public void deleteOrphanTest() {
        // 기존 자식 여부 확인
        Parent p = parentRepository.findById(102L).get();
        // FetchType 이 LAZY 인 경우 오류 발생
        System.out.println("기존 자식 " + p.getChildList());

        p.getChildList().remove(0); // 인덱스 제거 => childList 에서 제거 => 엔티티 제거
        parentRepository.save(p);

    }
}
