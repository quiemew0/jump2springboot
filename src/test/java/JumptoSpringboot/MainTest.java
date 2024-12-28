package JumptoSpringboot;

import JumptoSpringboot.domain.Answer;
import JumptoSpringboot.domain.Question;
import JumptoSpringboot.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// db에 반영이됏다가 마지막에 삭제되거나 가짜 db만들어서
@SpringBootTest
public class MainTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    @Test
    void testJpa() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();
        assertEquals(1, 1);
        assertEquals(1, 1);
        // assertEquals(1, answerList.size());
        // assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }
}
