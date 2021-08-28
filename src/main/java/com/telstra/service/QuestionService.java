package com.telstra.service;

import com.telstra.dto.CommentResponse;
import com.telstra.dto.QuestionRequest;
import com.telstra.dto.QuestionResponse;
import com.telstra.model.Comment;
import com.telstra.model.Question;
import com.telstra.model.Tag;
import com.telstra.repository.CommentRepository;
import com.telstra.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@Slf4j
@Transactional
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AuthService authService;
    @Autowired
    TagService tagService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    Mapper mapper;
    @Autowired
    GetterSource getterSource;
    @Autowired
    UserService userService;



    public QuestionResponse createQuestion(QuestionRequest questionDto) {
        Question question = new Question();

        question.setCreatedDate(Instant.now());
        question.setDescription(questionDto.getDescription());
        question.setPostName(questionDto.getPostName());
        question.setUser(authService.getCurrentUser());
        question.setDownVoteCount(0);
        question.setUpVoteCount(0);
        Tag newTag = (Tag) tagService.createTag(questionDto.getTag());

        question.setTag(newTag);

        question = questionRepository.save(question);
        userService.incrementUserPoints(authService.getCurrentUser().getUserId(), 20L);


        return mapper.mapQuestion(question);

    }



    public List<QuestionResponse> getQues() {
        List<Question> qList = questionRepository.findAll();
        List<QuestionResponse> sorted = new ArrayList<QuestionResponse>();

        qList.sort(Comparator.comparingInt(Question::getUpVoteCount));

        for (int i = 0; i < Math.min(qList.size(), 10); i++) {
            sorted.add(mapper.mapQuestion(qList.get(i)));
            sorted.get(i).setComments(getterSource.getQuestionComments(qList.get(i).getPostId()));
        }
        return sorted;
    }

    public QuestionResponse getQuesById(Long id) {
        Question question = questionRepository.findById(id).get();
        QuestionResponse myQuestion = mapper.mapQuestion(question);
        myQuestion.setComments(getterSource.getQuestionComments(question.getPostId()));
        return myQuestion;
    }

    public String upVote(Long id) {
        Question question = questionRepository.findById(id).get();
        if (question.getUpVoteCount() == null) {
            question.setUpVoteCount(0);
        }
        question.setUpVoteCount(question.getUpVoteCount() + 1);
        questionRepository.save(question);
        return "question with title" + question.getPostName() + "upvoted";
    }

    public String downVote(Long id) {
        Question question = questionRepository.findById(id).get();
        if (question.getDownVoteCount() == null) {
            question.setDownVoteCount(0);
        }
        question.setDownVoteCount(question.getDownVoteCount() + 1);
        questionRepository.save(question);
        return "question with title" + question.getPostName() + "downvoted";
    }

    public String deleteQuestion(Long id){
        Question q=questionRepository.findById(id).get();
        if(q.getUser().getUserId()!=authService.getCurrentUser().getUserId()){
            return "you dont have the privledge to delete this question";
        }
        commentRepository.deleteById(q.getPostId());
        return "This question is deleted by "+ authService.getCurrentUser().getUsername();
    }
}
