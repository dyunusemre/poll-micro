package com.yundi.pollquestionservice.question.service;

import com.yundi.pollquestionservice.question.dto.QuestionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {
    List<QuestionDto> getAllQuestions();

    List<QuestionDto> getAllWaitingQuestions();

    QuestionDto sendQuestionForApproval();

    QuestionDto approveQuestion();

    void deleteQuestion();
}
