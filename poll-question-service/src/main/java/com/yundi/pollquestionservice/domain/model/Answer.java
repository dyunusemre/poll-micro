package com.yundi.pollquestionservice.domain.model;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Answer {
    private int answerNo;
    private String answer;
}
