package com.mysite.sbb;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
}