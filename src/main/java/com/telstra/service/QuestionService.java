package com.telstra.service;

import com.telstra.dto.QuestionDto;
import com.telstra.dto.TagDto;
import com.telstra.model.Question;
import com.telstra.model.Tag;
import com.telstra.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AuthService authService;
    @Autowired
    TagService tagService;

    public Question createQuestion(QuestionDto questionDto) {
        Question question=new Question();

        question.setCreatedDate(Instant.now());
        question.setDescription(questionDto.getDescription());
        question.setPostName(questionDto.getPostName());
        question.setUser(authService.getCurrentUser());
        Optional<Tag> newTag= (Optional<Tag>) tagService.createTag(questionDto.getTag());

        question.setTag(newTag.get());

        return questionRepository.save(question);

    }

    public List<Question> getQues() {
        System.out.println(questionRepository.findAll()+"*******************************");
        return questionRepository.findAll();
//        List<Question> qList=questionRepository.findAll();
//        qList.sort(Comparator.comparingInt(a -> a.getDownVoteCount() + a.getUpVoteCount()));
//        List<Question>sorted=new ArrayList<Question>();
//        return qList.subList(0,Math.min(qList.size(),11));
    }

    public Question getQuesById(Long id) {
        return questionRepository.findById(id).get();
    }
}
