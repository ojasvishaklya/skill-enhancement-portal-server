package com.telstra.service;

import com.telstra.dto.QuestionRequest;
import com.telstra.dto.QuestionResponse;
import com.telstra.dto.SearchRequest;
import com.telstra.dto.UserResponse;
import com.telstra.exceptions.EntityNotFoundException;
import com.telstra.model.Comment;
import com.telstra.model.Question;
import com.telstra.model.Tag;
import com.telstra.repository.CommentRepository;
import com.telstra.repository.QuestionRepository;
import com.telstra.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
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
    @Autowired
    TagRepository tagRepository;

    String questionNotFound = "No question found with that id : ";

    public QuestionResponse createQuestion(QuestionRequest questionDto) {
        Question question = new Question();

        question.setCreatedDate(Instant.now());
        question.setDescription(questionDto.getDescription());
        question.setPostName(questionDto.getPostName());
        question.setUser(authService.getCurrentUser());
        question.setDownVoteCount(0);
        question.setUpVoteCount(0);
        question.setUrl(questionDto.getUrl());
        Tag newTag = tagService.createTag(questionDto.getTag());

        question.setTag(newTag);

        question = questionRepository.save(question);
        userService.incrementUserPoints(authService.getCurrentUser().getUserId(), 20L);


        return mapper.mapQuestion(question);

    }


    public List<QuestionResponse> getQues() {
        List<Question> qList = questionRepository.findAll();
        List<QuestionResponse> sorted = new ArrayList<>();

        qList.sort((a, b) -> {
            a.setUpVoteCount(a.getUpVoteCount() == null ? 0 : a.getUpVoteCount());
            b.setUpVoteCount(b.getUpVoteCount() == null ? 0 : b.getUpVoteCount());
            return b.getUpVoteCount() - a.getUpVoteCount();
        });

        for (int i = 0; i < Math.min(qList.size(), 10); i++) {
            sorted.add(mapper.mapQuestion(qList.get(i)));
            sorted.get(i).setComments(getterSource.getQuestionComments(qList.get(i).getPostId()));
        }
        return sorted;
    }

    public QuestionResponse getQuesById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("" + id)
        );
        QuestionResponse myQuestion = mapper.mapQuestion(question);
        myQuestion.setComments(getterSource.getQuestionComments(question.getPostId()));
        return myQuestion;
    }

    public String upVote(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(questionNotFound + id)
        );
        if (question.getUpVoteCount() == null) {
            question.setUpVoteCount(0);
        }
        question.setUpVoteCount(question.getUpVoteCount() + 1);
        questionRepository.save(question);
        return "question with title" + question.getPostName() + "upvoted";
    }

    public String downVote(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(questionNotFound + id)
        );
        if (question.getUpVoteCount() == null) {
            question.setUpVoteCount(0);
        }
        question.setUpVoteCount(question.getUpVoteCount() - 1);
        questionRepository.save(question);
        return "question with title" + question.getPostName() + " downvoted";
    }

    public String deleteQuestion(Long id) {
        Question q = questionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(questionNotFound + id)
        );
        if (!q.getUser().getUserId().equals(authService.getCurrentUser().getUserId())) {
            return "you dont have the privledge to delete this question";
        }
        List<Comment> commentList = commentRepository.findAll();

        for (Comment c : commentList) {
            if (c.getQuestion().getPostId().equals(id)) {
                commentRepository.deleteById(c.getId());
            }
        }
        List<Tag> tagList = tagRepository.findAll();

//        for(Tag c : tagList){
//            if(c. ==id){
//                commentRepository.deleteById(c.getQuestion().getPostId());
//            }
//        }
        questionRepository.deleteById(q.getPostId());
        return "This question is deleted by " + authService.getCurrentUser().getUsername();
    }

    public List<QuestionResponse> searchQuestion(SearchRequest searchRequest) {
        String[] splited = searchRequest.getText().split("\\s+");
        String searchQuery = "";
        for (String s : splited) {
            searchQuery += s + "* ";
        }
        List<Question> qList = questionRepository.searchQuestion(searchQuery);
        List<QuestionResponse> sorted = new ArrayList<>();


        for (int i = 0; i < Math.min(qList.size(), 10); i++) {
            sorted.add(mapper.mapQuestion(qList.get(i)));
            sorted.get(i).setComments(getterSource.getQuestionComments(qList.get(i).getPostId()));
        }
        return sorted;
    }

    public List<QuestionResponse> getFeed(Long id) {
        List<UserResponse> followers = getterSource.getUserFollowing(id);
        List<QuestionResponse> result=new ArrayList<>();
        for(UserResponse user : followers) {
            List<QuestionResponse> questions = getterSource.getUserQuestions(user.getId());
            for(QuestionResponse q : questions) {
                result.add(q);
            }
        }
        result.sort((a, b) -> {
            return b.getInstant().compareTo(a.getInstant());
        });
        return  result;
    }

}
