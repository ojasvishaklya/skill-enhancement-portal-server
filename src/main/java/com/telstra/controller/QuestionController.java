package com.telstra.controller;


import com.telstra.dto.QuestionRequest;
import com.telstra.dto.QuestionResponse;
import com.telstra.dto.SearchRequest;
import com.telstra.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping("/ques")
    public QuestionResponse createQuestion(@RequestBody QuestionRequest questionDto) {
        return questionService.createQuestion(questionDto);
    }

    @GetMapping("/ques/trending")
    public List<QuestionResponse> getQues() {
        return questionService.getQues();
    }

    @GetMapping("/ques/{id}")
    public QuestionResponse getQuesById(@PathVariable Long id) {
        return questionService.getQuesById(id);
    }

    @PostMapping("/ques/search")
    public List<QuestionResponse> searchQuestion(@RequestBody SearchRequest searchRequest) {
        return questionService.searchQuestion(searchRequest);
    }

    @PostMapping("/ques/{id}/upvote")
    public String upVote(@PathVariable Long id) {
        return questionService.upVote(id);
    }

    @PostMapping("/ques/{id}/downvote")
    public String downVote(@PathVariable Long id) {
        return questionService.downVote(id);
    }

    @DeleteMapping("/ques/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }


}
