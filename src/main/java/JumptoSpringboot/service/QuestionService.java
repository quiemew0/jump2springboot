package JumptoSpringboot.service;

import JumptoSpringboot.domain.Question;
import JumptoSpringboot.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        /// do not code without understanding.
        /// if there's some error on your test,
        //// you should remove code without understanding first
        return question.orElseThrow(() -> new IllegalArgumentException("Error!"));
    }

    public Question create(String subject, String content) {
        Question question = new Question();
        question.setId(null);
        question.setCreateDate(LocalDateTime.now());
        question.setSubject(subject);
        question.setContent(content);
        question.setAnswerList(new ArrayList<>());
        return questionRepository.save(question);
    }
}
