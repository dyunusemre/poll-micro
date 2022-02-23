package com.yundi.pollquestionservice.question.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    @Retry(name = "question-all", fallbackMethod = "staticResponse")
    @GetMapping("/all")
    public String getPollQuestions() {
        log.info("All Question retried");
        throw new RuntimeException("Test");
    }

    public String staticResponse(Exception ex) {
        return "Question 1";
    }

    @CircuitBreaker(name = "question-waiting", fallbackMethod = "staticResponse")
    @GetMapping("/waiting")
    public String getPollWaitingQuestions() {
        log.info("Circuit Breaker All Question retried");
        throw new RuntimeException("Test");
    }

    @PostMapping("/approve")
    public void approveWaitingQuestions() {

    }

    @PostMapping("/retrive-answer")
    public String retrieveUserAnswer(@RequestBody String request) {
        return null;
    }

    @RateLimiter(name = "question-answer")
    @PutMapping("/send-answer")
    public void sendAnswer() {
        //questionComponent.saveAnswer(request.getQuestionId(), request.getUserId(), request.getOptionNo());
    }

    @PostMapping("/create")
    public void createQuestion(@RequestBody String question) {
    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable String id) {
    }

    @PatchMapping("/modify")
    public void modifyQuestion() {
    }

    @GetMapping("/answer")
    public List<String> getPollAnswers() {
        return null;
    }
}
