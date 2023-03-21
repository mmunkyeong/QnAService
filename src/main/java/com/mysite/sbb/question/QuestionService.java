package com.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service // 서비스로 인식
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList(){ //질문 목록 조회
        return this.questionRepository.findAll();
    }
}
