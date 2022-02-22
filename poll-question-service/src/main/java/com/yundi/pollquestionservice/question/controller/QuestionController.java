package com.yundi.pollquestionservice.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/question")
@RequiredArgsConstructor
public class QuestionController {

    @GetMapping("/all")
    public String getPollQuestions() {
        return null;
    }

    @GetMapping("/waiting")
    public String getPollWaitingQuestions() {
        return null;
    }

    @PostMapping("/approve")
    public void approveWaitingQuestions() {

    }

    @PostMapping("/retrive-answer")
    public String retrieveUserAnswer(@RequestBody String request) {
        return null;
    }

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
