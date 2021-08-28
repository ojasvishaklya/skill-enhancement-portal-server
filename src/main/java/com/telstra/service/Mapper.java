package com.telstra.service;

import com.telstra.dto.CommentResponse;
import com.telstra.dto.QuestionResponse;
import com.telstra.dto.UserProfileResponse;
import com.telstra.dto.UserResponse;
import com.telstra.model.Comment;
import com.telstra.model.Question;
import com.telstra.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    @Autowired
    GetterSource getterSource;

    public QuestionResponse mapQuestion(Question question) {

        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setDescription(question.getDescription());
        questionResponse.setTag(question.getTag().getName());
        questionResponse.setPostName(question.getPostName());
        questionResponse.setDownvotes(question.getDownVoteCount() == null ? 0 : question.getDownVoteCount());
        questionResponse.setUpvotes(question.getUpVoteCount() == null ? 0 : question.getUpVoteCount());
        questionResponse.setId(question.getPostId());
        return questionResponse;
    }

    public CommentResponse mapComment(Comment comment) {

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCommentername(comment.getUser().getUsername());
        commentResponse.setInstant(comment.getCreatedDate().toString());
        commentResponse.setText(comment.getText());
        commentResponse.setSelected(comment.isSelected());
        commentResponse.setDownvotes(comment.getDownVoteCount() == null ? 0 : comment.getDownVoteCount());
        commentResponse.setUpvotes(comment.getUpVoteCount() == null ? 0 : comment.getUpVoteCount());
        commentResponse.setId(comment.getId());
        return commentResponse;
    }

    public UserProfileResponse mapUser(User user) {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setCommentList(getterSource.getUserComments(user.getUserId()));
        userProfileResponse.setQuestionList(getterSource.getUserQuestions(user.getUserId()));
        userProfileResponse.setEmail(user.getEmail());
        userProfileResponse.setGithub(user.getGithub() == null ? "" : user.getGithub());
        userProfileResponse.setGithub(user.getLinkedin() == null ? "" : user.getLinkedin());
        userProfileResponse.setPoints(user.getPoints() == null ? 0 : user.getPoints());
        userProfileResponse.setName(user.getUsername());
        userProfileResponse.setId(user.getUserId());
        return userProfileResponse;
    }

    public UserResponse mapUserMin(User user) {
        UserResponse userResponse = new UserResponse();

        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getUsername());
        userResponse.setId(user.getUserId());
        return userResponse;
    }
}
