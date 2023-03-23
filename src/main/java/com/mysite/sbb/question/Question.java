package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
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
    // OneToMany 자바세상에서의 편의를 위해서 필드 생성
    // 이 녀석은 실제 DB 테이블에 칼럼이 생성되지 않는다.
    // DB는 배열이나 리스트를 저장할 수 없다.
    // 칼럼에 저장할 수 없다.
    // 만들어도 되고 안 만들어도 ok
    // 다만 만들면 해당 객체(질문객체)에서 관련된 답변들을 찾을 때 편합니다.
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    // OneToMany 애너테이션에 사용된 mappedBy는 참조 엔티티의 속성명
    // question을 answer에 전달
    // OneToMany 에는 직접객체초기화
    private List<Answer> answerList=new ArrayList<>();

    public void addAnswer(Answer a) {
        a.setQuestion(this);
        answerList.add(a);
    }
    @ManyToOne
    private SiteUser author; // 여러개의 질문이 한명의 사용자에게 작성될 수 있음

    private LocalDateTime modifyDate; //수정일시

    @ManyToMany
    Set<SiteUser> voter; // 추천인은 중복 x =>set
}