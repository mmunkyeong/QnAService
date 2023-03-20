package com.mysite.sbb;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // Entity애너테이션을 적용해야 JPA가 엔티티로 인식
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 고유번호 생성 옵션, 해당 컬럼만의 독립적인 시퀀스를 생성
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT") //글자수를 제한할 수 없는 경우
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    // OneToMany 애너테이션에 사용된 mappedBy는 참조 엔티티의 속성명
    // question을 answer에 전달
    private List<Answer> answerList;
}