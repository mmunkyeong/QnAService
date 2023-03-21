package com.mysite.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/question") //url 프리픽스
@RequiredArgsConstructor //롬북이 제공, final이 붙은 속성을 포함하는 생성자를 자동으로 생성
@Controller
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list") //question/list 경로와 같음
    public String list(Model model){
        List<Question> questionList=this.questionService.getList();
        model.addAttribute("questionList",questionList);
        return "question_list";
    }

    @GetMapping(value="/detail/{id}") //id와 PathVariable id와 동일 해야함
    public String detail(Model model, @PathVariable("id") Integer id){
        Question question=this.questionService.getQuestion(id);
        model.addAttribute("question",question);
        return "question_detail";
    }
}

