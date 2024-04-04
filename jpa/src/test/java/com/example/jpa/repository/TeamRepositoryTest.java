package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Team;
import com.example.jpa.entity.TeamMember;

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

    @Test
    public void getRowTest() {
        // 팀멤버 한명 찾기
        TeamMember teamMember = teamMemberRepository.findById("member1").get();
        System.out.println(teamMember); // TeamMember(id=member1, userName=이춘향, team=Team(id=team1, name=슈퍼민주주의))
        // select tm1_0.member_id, t1_0.team_id, t1_0.team_name, tm1_0.user_name
        // from team_member tm1_0 left join team t1_0 on t1_0.team_id=tm1_0.team_team_id
        // where tm1_0.member_id=?

    }

}
