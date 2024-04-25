package com.example.movie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // @Query("select m, min(mi), avg(r.grade), count(distinct r) from Movie m left
    // join MovieImage mi on mi.movie = m left join Review r on r.movie = m group by
    // m") => 요건 에러남
    // @Query("select m, avg(r.grade), count(distinct r) from Movie m left join
    // Review r on r.movie= m group by m") => 요건 안남
    // Page<Object[]> getListPage2(Pageable pageable);

    @Query(value = "SELECT * FROM MOVIE m LEFT JOIN (SELECT r.MOVIE_MNO, COUNT(*), " +
            "AVG(r.GRADE) FROM REVIEW r GROUP BY r.MOVIE_MNO) r1 ON m.mno = r1.movie_mno " +
            " LEFT JOIN (SELECT mi2.* FROM	MOVIE_IMAGE mi2 WHERE mi2.INUM IN " +
            "(SELECT MIN(mi.INUM) FROM MOVIE_IMAGE mi " +
            "GROUP BY mi.MOVIE_MNO)) a ON	m.MNO = a.movie_mno", nativeQuery = true)
    Page<Object[]> getListPage(Pageable pageable);
}

// 원래 만들었던 쿼리문 에러떠서 페지됨 ㅠㅠ
// "SELECT mi.IMGNAME, mi.PATH, mi.UUID, m.MNO, m.CREATED_DATE, m.TITLE, " +
// "(SELECT COUNT(DISTINCT r.review_no) FROM REVIEW r WHERE r.MOVIE_MNO = m.MNO)
// AS review_cnt, " +
// "(SELECT AVG(r.GRADE) FROM REVIEW r WHERE r.MOVIE_MNO = m.MNO) AS grade_avg "
// +
// "FROM MOVIE_IMAGE mi JOIN movie m ON mi.MOVIE_MNO = m.MNO " +
// "WHERE mi.INUM IN (SELECT MIN(mi2.INUM) FROM MOVIE_IMAGE mi2 GROUP BY
// mi2.MOVIE_MNO) "