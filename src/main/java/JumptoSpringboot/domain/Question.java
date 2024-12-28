package JumptoSpringboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // property 명 question을 적음
    private List<Answer> answerList; //타입은 리스트

    public Question(){

    }
    public Question(Integer id, String subject, String content, LocalDateTime createDate, List<Answer> answerList) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.createDate = createDate;
        this.answerList = answerList;
    }
}
