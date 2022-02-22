package com.yundi.pollquestionservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document("user-answer")
public class UserAnswer {
    @Id
    private String id;
    private String userId;
    private String questionId;
    private String answerNo;
}
