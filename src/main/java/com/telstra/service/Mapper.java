package com.telstra.service;

import com.telstra.dto.*;
import com.telstra.model.Comment;
import com.telstra.model.Notification;
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
        questionResponse.setTagId(question.getTag().getId().toString());
        questionResponse.setPostName(question.getPostName());
        questionResponse.setDownvotes(question.getDownVoteCount() == null ? 0 : question.getDownVoteCount());
        questionResponse.setUpvotes(question.getUpVoteCount() == null ? 0 : question.getUpVoteCount());
        questionResponse.setId(question.getPostId());
        questionResponse.setCreator(question.getUser().getUsername());
        questionResponse.setInstant(question.getCreatedDate().toString());
        questionResponse.setCreatorId(question.getUser().getUserId().toString());
        return questionResponse;
    }

    public CommentResponse mapComment(Comment comment) {

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setInstant(comment.getCreatedDate().toString());
        commentResponse.setText(comment.getText());
        commentResponse.setSelected(comment.isSelected());
        commentResponse.setDownvotes(comment.getDownVoteCount() == null ? 0 : comment.getDownVoteCount());
        commentResponse.setUpvotes(comment.getUpVoteCount() == null ? 0 : comment.getUpVoteCount());
        commentResponse.setId(comment.getId());
        commentResponse.setCreator(comment.getUser().getUsername());
        commentResponse.setCreatorId(comment.getUser().getUserId().toString());
        return commentResponse;
    }

    public UserProfileResponse mapUser(User user) {
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setCommentList(getterSource.getUserComments(user.getUserId()));
        userProfileResponse.setQuestionList(getterSource.getUserQuestions(user.getUserId()));
        userProfileResponse.setFollowersList(getterSource.getUserFollowers(user.getUserId()));
        userProfileResponse.setFollowingList(getterSource.getUserFollowing(user.getUserId()));
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
        userResponse.setPoints(user.getPoints() == null ? "0" : user.getPoints().toString());
        return userResponse;
    }

    public NotificationResponse mapNotification(Notification n) {
        NotificationResponse myNotification = new NotificationResponse();
        myNotification.setId(n.getId());
        myNotification.setInstant(n.getInstant().toString());
        myNotification.setText(n.getNotification());
        return myNotification;
    }
}
