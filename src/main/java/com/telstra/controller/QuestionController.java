package com.telstra.controller;


import com.telstra.dto.QuestionDto;
import com.telstra.model.Question;
import com.telstra.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @PostMapping("/ques")
    public Question createQuestion(@RequestBody QuestionDto questionDto){

        System.out.println("**************************************");
        return questionService.createQuestion(questionDto);
    }
    @GetMapping("/ques")
    public List<Question> getQues(){
        return questionService.getQues();
    }
    @GetMapping("/ques/{id}")
    public Question getQuesById(@PathVariable Long id){
        return questionService.getQuesById(id);
    }


}
