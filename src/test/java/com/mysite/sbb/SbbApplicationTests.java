package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionService questionService;

    @BeforeEach //아래 메서드는 각 테스트 케이스가 실행되기전에 실행
    void beforeEach(){
        //모든 데이터 삭제
        questionRepository.deleteAll();

        //흔적 삭제(다음번 insert때 id가 1이 되도록)
        questionRepository.clearAutoIncrement();

        //질문 1개 생성
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        //질문 1개 생성
        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);

        // 답변 1개 생성
        Answer a1 = new Answer();
        a1.setContent("네 자동으로 생성됩니다.");
        q2.addAnswer(a1);
        a1.setCreateDate(LocalDateTime.now());
        answerRepository.save(a1);
    }
    @Test
    @DisplayName("데이터 저장하기")

    void test1() {
        // 질문 1개 생성
        Question q = new Question();
        q.setSubject("세계에서 가장 부유한 국가가 어디인가요?");
        q.setContent("알고 싶습니다.");
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);

        assertEquals("세계에서 가장 부유한 국가가 어디인가요?", questionRepository.findById(3).get().getSubject());

    }

    @Test
    @DisplayName("데이터 조회하기, findAll")
    void test2() {
        //find all 모든 데이터를 조회
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        //assertEquals(기대값, 실제값) 기대값과 실제값이 동일한지 조사
        //동일하지 않다면 테스트는 실패
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    @DisplayName("아이디로 데이터 조회, findById")
    void test3() {
        Optional<Question> oq = questionRepository.findById(1);

        if (oq.isPresent()) {
            Question q = oq.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }
    @Test
    @DisplayName("제목으로 데이터 조회")
    void test4() {
        Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1, q.getId());
    }
    @Test
    @DisplayName("제목, 내용으로 데이터 조회 ")
    void test5() {
        // and 여러컬럼을 and로 검색
        Question q = this.questionRepository.findBySubjectAndContent(
                "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1, q.getId());
    }

    @Test
    @DisplayName("특정 문자열이 포함되어 있는 데이터 조회")
    void test6(){
        List<Question> questionList=this.questionRepository.findBySubjectLike("sbb%");
        Question q=questionList.get(0);
        assertEquals("sbb가 무엇인가요?",q.getSubject());
    }

    @Test
    @DisplayName("데이터 수정하기")
    void test7() {
        //findby리턴타입은 Question이 아닌 Optional
        // Optional 은 null 처리하기 위해 사용
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");
        this.questionRepository.save(q);
    }


    @Test
    @DisplayName("데이터 삭제하기")
    void test8() {
       assertEquals(2,this.questionRepository.count());
       Optional<Question> oq=this.questionRepository.findById(1);
       assertTrue(oq.isPresent());
       Question q=oq.get();
       this.questionRepository.delete(q);
       assertEquals(1,this.questionRepository.count());
    }

    @Test
    @DisplayName("답변 데이터 생성 후 저장하기")
    void test9(){
        Optional<Question>oq=this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q=oq.get();

        Answer a=new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q); //어떤 질문의 답변인지 알기 위해 Question 객체 필요

        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }

    @Test
    @DisplayName("답변 조회하기")
    void test10() {
        //id값이 1인 답변을 조회
        Optional<Answer> oa = answerRepository.findById(1);
        assertTrue(oa.isPresent()); //isPresent()Boolean 타입
        // Optional 객체가 값을 가지고 있다면 true, 없다면 false
        Answer a = oa.get();
        assertEquals(2, a.getQuestion().getId());
    }

    @Transactional //메서드가 종료될 때까지 DB세션 유지
    @Rollback(false)
    @Test
    @DisplayName("질문에 달린 답변 찾기")
    void t011() {
        Optional<Question> oq = questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }

    @Test
    @DisplayName("대량 테스트 데이터 만들기")
    void t012() {
        for(int i=1; i<=300; i++){
            String subject=String.format("테스트 데이터입니다: [%03d]",i);
            String content="내용무";
            this.questionService.create(subject,content);
        }
    }
}