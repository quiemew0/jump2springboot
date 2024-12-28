package JumptoSpringboot.controller;


import JumptoSpringboot.domain.Question;
import JumptoSpringboot.service.AnswerService;
import JumptoSpringboot.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


//@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
/// (value = "content")
@PostMapping("/answer/create/{id}")
    public String createAnswer(@PathVariable("id") Integer id,
                               @RequestParam (value = "content") String content) {
        //Question question = this.questionService.getQuestion(id);
        //this.answerService.create(question, content);
        return String.format("redirect:/question/detail/%s", id);
    }
}
