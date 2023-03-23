package com.mysite.sbb.question;

import com.mysite.sbb.question.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findAll(Pageable pageable);

    // @Modifying // 만약 아래 쿼리 select가 아니라면 이걸 붙여야 한다.
    // nativeQuery = true 여야 MySQL 쿼리문법 사용 가능
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
    void clearAutoIncrement();

    Page<Question> findAll(Specification<Question>spec,Pageable pageable);
 // 쿼리를 작성할 때 테이블 기준이 아닌 엔티티 기준으로 작성
    // site_user 테이블명 대신 SiteUser처럼 엔티티명을 사용

    @Query("select "
    +"distinct q "
    + "from Question q "
    + "left outer join SiteUser u1 on q.author=u1 "
    + "left outer join Answer a on a.question=q "
    + "left outer join SiteUser u2 on a.author=u2 "
    + "where "
    + "   q.subject like %:kw% "
    + "   or q.content like %:kw% "
    + "   or u1.username like %:kw% "
    + "   or a.content like %:kw% "
    + "   or u2.username like %:kw% ")


 Page<Question>findAllByKeyword(@Param("kw")String kw,Pageable pageable);
}
