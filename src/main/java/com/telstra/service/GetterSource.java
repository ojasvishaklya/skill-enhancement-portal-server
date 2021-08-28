package com.telstra.service;

import com.telstra.dto.CommentResponse;
import com.telstra.dto.QuestionResponse;
import com.telstra.model.Comment;
import com.telstra.model.Question;
import com.telstra.repository.CommentRepository;
import com.telstra.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetterSource {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    Mapper mapper;
    @Autowired
    QuestionRepository questionRepository;

    //====================================Question retriever=================================
    //Returns all questions of one user
    public List<QuestionResponse> getUserQuestions(Long id) {
        List<Question> questionList = questionRepository.findAll();
        List<QuestionResponse> myQuestion = new ArrayList<QuestionResponse>();

        for (Question q : questionList) {
            if (q.getUser().getUserId() == id) {
                myQuestion.add(mapper.mapQuestion(q));
            }
        }
        return myQuestion;
    }

    //Returns all questions of one Tag
    public List<QuestionResponse> getTagQuestions(String tag) {
        List<Question> questionList = questionRepository.findAll();
        List<QuestionResponse> myQuestion = new ArrayList<QuestionResponse>();

        for (Question q : questionList) {
            if (q.getTag().getName() == tag) {
                myQuestion.add(mapper.mapQuestion(q));
            }
        }
        return myQuestion;
    }


    //====================================Comment retriever=================================
    //Returns all comments to a question based on Id
    public List<CommentResponse> getQuestionComments(Long id) {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponse> myComments = new ArrayList<CommentResponse>();

        for (Comment c : commentList) {
            if (c.getQuestion().getPostId() == id) {
                if (c.isSelected()) {
                    myComments.add(0, mapper.mapComment(c));
                } else
                    myComments.add(mapper.mapComment(c));
            }
        }
        return myComments;
    }

    //Returns all comments of a particular user
    public List<CommentResponse> getUserComments(Long id) {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponse> myComments = new ArrayList<CommentResponse>();

        for (Comment c : commentList) {
            if (c.getUser().getUserId() == id) {
                if (c.isSelected()) {
                    myComments.add(0, mapper.mapComment(c));
                } else
                    myComments.add(mapper.mapComment(c));
            }
        }
        return myComments;
    }

    //Returns all comments of a particular Tag
    public List<CommentResponse> getTagComments(String tag) {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponse> myComments = new ArrayList<CommentResponse>();

        for (Comment c : commentList) {
            if (c.getQuestion().getTag().getName() == tag) {
                if (c.isSelected()) {
                    myComments.add(0, mapper.mapComment(c));
                } else
                    myComments.add(mapper.mapComment(c));
            }
        }
        return myComments;
    }
}
