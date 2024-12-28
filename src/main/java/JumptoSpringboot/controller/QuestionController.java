package JumptoSpringboot.controller;

import JumptoSpringboot.domain.Question;
import JumptoSpringboot.service.AnswerService;
import JumptoSpringboot.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping("/question/detail/{id}")
    public String detail(@PathVariable("id") Integer id) {
        //Question question = this.questionService.getQuestion(id);
        //model.addAttribute("question", question);
        System.out.println("hi");
        return "question_detail";
    }

}
