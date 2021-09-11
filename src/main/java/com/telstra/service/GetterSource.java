package com.telstra.service;

import com.telstra.dto.CommentResponse;
import com.telstra.dto.QuestionResponse;
import com.telstra.dto.UserResponse;
import com.telstra.model.Comment;
import com.telstra.model.Followers;
import com.telstra.model.Question;
import com.telstra.repository.CommentRepository;
import com.telstra.repository.FollowersRepository;
import com.telstra.repository.QuestionRepository;
import com.telstra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @Autowired
    FollowersRepository followersRepository;
    @Autowired
    UserRepository userRepository;


    //====================================Followers retriever=================================
    public List<UserResponse> getUserFollowers(Long id) {
        List<UserResponse> followedBy = new ArrayList<>();
        List<Followers> followers = followersRepository.findAll();
        for (Followers s : followers) {
            if (s.getUserId().equals(id))
                followedBy.add(mapper.mapUserMin(userRepository.findById(s.getFollowerId()).orElseThrow(
                        () -> new UsernameNotFoundException("User not found")
                )));
        }
        return followedBy;
    }
    public List<UserResponse> getUserFollowing(Long id) {
        List<UserResponse> follows = new ArrayList<>();
        List<Followers> followers = followersRepository.findAll();
        for (Followers s : followers) {
            if (s.getFollowerId().equals(id))
                follows.add(mapper.mapUserMin(userRepository.findById(s.getUserId()).orElseThrow(
                        () -> new UsernameNotFoundException("No such user found")
                )));
        }
        return follows;
    }
    //====================================Question retriever=================================
    //Returns all questions of one user
    public List<QuestionResponse> getUserQuestions(Long id) {
        List<Question> questionList = questionRepository.findAll();
        List<QuestionResponse> myQuestion = new ArrayList<>();

        for (Question q : questionList) {
            if (q.getUser().getUserId().equals(id)) {
                myQuestion.add(mapper.mapQuestion(q));
            }
        }
        return myQuestion;
    }

    //Returns all questions of one Tag
    public List<QuestionResponse> getTagQuestions(String tag) {
        List<Question> questionList = questionRepository.findAll();
        List<QuestionResponse> myQuestion = new ArrayList<>();

        for (Question q : questionList) {
            if (q.getTag().getName().equals(tag)) {
                myQuestion.add(mapper.mapQuestion(q));
            }
        }
        return myQuestion;
    }


    //====================================Comment retriever=================================
    //Returns all comments to a question based on Id
    public List<CommentResponse> getQuestionComments(Long id) {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentResponse> myComments = new ArrayList<>();

        for (Comment c : commentList) {
            if (c.getQuestion().getPostId().equals(id)) {
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
        List<CommentResponse> myComments = new ArrayList<>();

        for (Comment c : commentList) {
            if (c.getUser().getUserId().equals(id)) {
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
        List<CommentResponse> myComments = new ArrayList<>();

        for (Comment c : commentList) {
            if (c.getQuestion().getTag().getName().equals(tag)) {
                if (c.isSelected()) {
                    myComments.add(0, mapper.mapComment(c));
                } else
                    myComments.add(mapper.mapComment(c));
            }
        }
        return myComments;
    }
}
