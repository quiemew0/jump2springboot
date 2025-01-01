package JumptoSpringboot.controller;

import JumptoSpringboot.domain.Question;
import JumptoSpringboot.domain.SiteUser;
import JumptoSpringboot.dto.AnswerForm;
import JumptoSpringboot.dto.QuestionForm;
import JumptoSpringboot.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.security.Principal;


@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    //value = "kw",defaultValue = "") : kw값이 ㄴ널이 되는것을 방지하기 위해 문자열을 기본값으로 설정
    @GetMapping("/question/list")
    public String list(Model model,@RequestParam(value="page",defaultValue = "0")int page,@RequestParam(value = "kw",defaultValue = "")String kw) {
        Page<Question> paging = this.questionService.getList(page,kw);
        model.addAttribute("paging",paging);
        model.addAttribute("kw", kw);
//        List<Question> questionList = this.questionService.getList();
///        model.addAttribute("questionList", questionList);
        return "question_list";
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


    @GetMapping("/question/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);// 404
        model.addAttribute("question", question);
        return "question_detail";
    }

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
    @PreAuthorize("isAuthenticated()")
    @GetMapping("question/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        System.out.println("요청은됏는뎅 재량이");
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("question/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        System.out.println("보내는것도? 재량이");
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("question/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
    // QuestionForm 의 subject, content 속성이 자동으로 바인딩.
//    @PostMapping("/question/create")
//    public String questionCreate(@RequestParam(value="subject") String subject, @RequestParam(value="content") String content) {
//        Question question = questionService.create(subject, content);
//        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
//    }
}
