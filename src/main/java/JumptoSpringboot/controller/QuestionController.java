package JumptoSpringboot.controller;

import JumptoSpringboot.domain.Question;
import JumptoSpringboot.service.AnswerService;
import JumptoSpringboot.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping("/question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);// 404
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PostMapping("/question")
    public ResponseEntity<Question> create(
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "content") String content
    ) {
        Question question = questionService.create(subject, content);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/question/create")
    public String questionCreate() {
        return "question_form";
    }
    @PostMapping("/question/create")
    public String questionCreate(@RequestParam(value="subject") String subject, @RequestParam(value="content") String content) {
        Question question = questionService.create(subject, content);
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }

}
