package com.example.vectorstorepostgres.controller;

import com.example.vectorstorepostgres.model.Answer;
import com.example.vectorstorepostgres.model.Question;
import com.example.vectorstorepostgres.service.QuestionAnswerDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/db")
@Slf4j
public class QuestionAnsweDbrController {

    private final QuestionAnswerDBService questionAnswerDBService;

    @GetMapping("/ask")
    public Answer askQuestion(@RequestParam String question) {
        log.info("answering question {}", question);
        return questionAnswerDBService.getAnswerForQuestion(new Question(question));
    }
}
