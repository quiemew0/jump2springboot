package JumptoSpringboot.controller;

import JumptoSpringboot.domain.Question;
import JumptoSpringboot.domain.SiteUser;
import JumptoSpringboot.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/question/list")
    public String list(Model model,@RequestParam(value="page",defaultValue = "0")int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging",paging);
//        List<Question> questionList = this.questionService.getList();
///        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping("/question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);// 404
        model.addAttribute("question", question);
        return "question_detail";
    }
/*
    @PostMapping("/question")
    public ResponseEntity<Question> create(
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "content") String content
    ) {
        Question question = questionService.create(subject, content, userService.);
        return ResponseEntity.ok(question);
    }*/

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/question/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";
    }
    // QuestionForm 의 subject, content 속성이 자동으로 바인딩.
//    @PostMapping("/question/create")
//    public String questionCreate(@RequestParam(value="subject") String subject, @RequestParam(value="content") String content) {
//        Question question = questionService.create(subject, content);
//        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
//    }
}
