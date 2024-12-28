package JumptoSpringboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne //부모 question(1), 자식 answer(n)
    private Question question;

    public Answer(){

    }
    public Answer(Integer id, String content, LocalDateTime createDate, Question question) {
        this.id = id;
        this.content = content;
        this.createDate = createDate;
        this.question = question;
    }
}
