package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message="제목은 필수항목입니다.") //빈값을 허용하지 않는다
    @Size(max=200) //최대 길이 200바이트가 넘으면 안된다
    private String subject;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
