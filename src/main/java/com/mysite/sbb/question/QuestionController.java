package com.mysite.sbb.question;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/detail/{id}") //id와 PathVariable id와 동일 해야함
    public String detail(Model model, @PathVariable("id") Integer id){
        Question question=this.questionService.getQuestion(id);
        model.addAttribute("question",question);
        return "question_detail";
    }
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }

    @PostMapping("/create")
    //메서드명 동일 매개변수의 형태가 다른경우 가능(메서드 오버로딩)
    //@Valid 애너테이션을 적용 하면 @NotEmpty, @Size등으로 설정한 검증 기능이 동작한다
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){
     // subject, content속성이 자동으로 바인딩
        if(bindingResult.hasErrors()){ //오류가 없을 경우에만 질문등록
           return "question_form";
        }
        this.questionService.create(questionForm.getSubject(),questionForm.getContent());
        return "redirect:/question/list"; //질문 저장 후 질문목록으로 이동
    }
}

