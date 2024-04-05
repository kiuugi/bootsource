package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;

import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamRepositoryTest {
        // 여기는 final이 들어올 수 없어서 autowired 가 강제됨
        @Autowired
        private TeamRepository teamRepository;

        @Autowired
        private TeamMemberRepository teamMemberRepository;

        @Test
        public void insertTest() {
                // 팀 정보 삽입
                Team team1 = teamRepository.save(Team.builder()
                                .id("team1")
                                .name("슈퍼민주주의")
                                .build());
                Team team2 = teamRepository.save(Team.builder()
                                .id("team2")
                                .name("통제된민주주의")
                                .build());
                Team team3 = teamRepository.save(Team.builder()
                                .id("team3")
                                .name("정돈된민주주의")
                                .build());

                // 팀 멤버 정보 삽입
                teamMemberRepository.save(TeamMember.builder()
                                .id("member1")
                                .userName("이춘향")
                                .team(team1)
                                .build());
                teamMemberRepository.save(TeamMember.builder()
                                .id("member2")
                                .userName("김진우")
                                .team(team2)
                                .build());
                teamMemberRepository.save(TeamMember.builder()
                                .id("member3")
                                .userName("김뚜띠")
                                .team(team2)
                                .build());
                teamMemberRepository.save(TeamMember.builder()
                                .id("member4")
                                .userName("감블러")
                                .team(team3)
                                .build());
        }

        // 연관관계가 있는 데이터 조회
        // 1. 다대일(멤버:팀) 관계에서는 기본적으로 1에 해당하는 엔티티 정보를 가지고 나옴
        // => FethType.EAGER 기본값
        // => 멤버를 조회시 팀 정보도 같이 가지고 나옴 => (객체 그래프 탐색으로 접근 가능)
        // => 객체지향 쿼리 작성
        // 2. 일대다(팀:멤버) 관계에서는 다에 해당하는 엔티티를 안 가지고 나옴
        // => FerhType.LAZY 기본값

        // => FethType : 연결관계에서 상대 정보를 같이 가지고 나올건지 말건지 여부
        // FethType.EAGER : 가지고 나옴
        // FethType.LAZY : 안가지고 나옴

        @Test
        public void getRowTest() {
                // 팀멤버 한명 찾기
                // team_member(n) : team(1) => 외래키 제약조건
                // member를 찾을 때 N:1 관계에서 1에 해당하는 정보도 기본으로 가지고 옴
                // ==> join 필요
                TeamMember teamMember = teamMemberRepository.findById("member1").get();
                System.out.println(teamMember); // TeamMember(id=member1, userName=이춘향, team=Team(id=team1,
                                                // name=슈퍼민주주의))
                // select tm1_0.member_id, t1_0.team_id, t1_0.team_name, tm1_0.user_name
                // from team_member tm1_0 left join team t1_0 on t1_0.team_id=tm1_0.team_team_id
                // where tm1_0.member_id=?
                // 객체 그래프 탐색 - teamMemberRepository 팀 멤버 정보를 불러왔지만 entity에 @ManyToOne 이런 어노테이션으로
                // team 정보도 같이 끌고와짐
                System.out.println("팀 전체 정보 : " + teamMember.getTeam()); // Team(id=team1, name=슈퍼민주주의)
                System.out.println("팀 명 : " + teamMember.getTeam().getName()); // 슈퍼민주주의

                // 회원 조회 시 나와 같은 팀에 소속된 회원과 팀 조회
                teamMemberRepository.findByMembersEqualTeam("통제된민주주의").forEach(member -> {
                        System.out.println(member);
                });
        }

        @Test
        public void updateTest() {
                // 멤버의 팀 변경
                TeamMember teamMember = teamMemberRepository.findById("member3").get();
                Team team = Team.builder().id("team3").build();
                teamMember.setTeam(team);

                System.out.println("수정 후 : " + teamMemberRepository.save(teamMember));

        }

        @Test
        public void deleteTest() {
                // 팀원 삭제 or 팀원 팀을 null 로 설정
                TeamMember member = teamMemberRepository.findById("member1").get();
                member.setTeam(null);
                teamMemberRepository.save(member);
                // 팀 삭제
                teamRepository.deleteById("team1");
        }

        // OneToMany 설정 기준으로
        // 팀을 기준으로 회원 조회
        // @Transactional
        @Test
        public void getMeemberList() {
                Team team = teamRepository.findById("team3").get();
                // LazyInitializationException : ToString() : members 변수를 출력하라고 했기 때문
                System.out.println(team);
                // org.hibernate.LazyInitializationException
                // 1. @Transactional 사용 / DB에서 트렌젝션 : 하나의 단위로 처리되는 작업
                // 2. FethType.EAGER 로 변경 / 여러 테이블에 조인할 경우 서버에 부담이 커질 수 있어서 잘 쓰지 않음
                team.getMembers().forEach(m -> System.out.println(m));

        }

}
