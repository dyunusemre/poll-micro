package com.yundi.pollquestionservice.domain.model;

import com.yundi.pollquestionservice.domain.enums.QuestionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document("question")
public class Question {
    @Id
    private String id;
    private String question;
    private QuestionStatus status;
    private List<Answer> answers;
}
