package JumptoSpringboot.service;

import JumptoSpringboot.domain.Question;
import JumptoSpringboot.domain.SiteUser;
import JumptoSpringboot.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Question create(String subject, String content, SiteUser user) {
        Question question = new Question();
        question.setId(null);
        question.setCreateDate(LocalDateTime.now());
        question.setSubject(subject);
        question.setContent(content);
        question.setAuthor(user);
        question.setAnswerList(new ArrayList<>());
        return questionRepository.save(question);
    }
    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 3,Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }
    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}
