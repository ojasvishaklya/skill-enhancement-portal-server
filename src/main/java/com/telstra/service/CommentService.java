package com.telstra.service;

import com.telstra.dto.CommentRequest;
import com.telstra.dto.CommentResponse;
import com.telstra.model.Comment;
import com.telstra.model.Question;
import com.telstra.repository.CommentRepository;
import com.telstra.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;


@Service
@Slf4j
@Transactional
public class CommentService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    AuthService authService;
    @Autowired
    Mapper mapper;
    @Autowired
    UserService userService;
    @Autowired
    NotificationService notificationService;

    String commentNotFound="No comment found with given id : ";

    public CommentResponse createComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setCreatedDate(Instant.now());
        comment.setUrl(commentRequest.getUrl());
        comment.setText(commentRequest.getText());
        Optional<Question> tempQuestion=questionRepository.findById(Long.parseLong(commentRequest.getQ_id()));
        if(tempQuestion.isPresent()){
            comment.setQuestion(tempQuestion.get());
        }
        comment.setUser(authService.getCurrentUser());
        comment.setDownVoteCount(0);
        comment.setUpVoteCount(0);
        comment.setUrl(commentRequest.getUrl());
        comment = commentRepository.save(comment);
        userService.incrementUserPoints(authService.getCurrentUser().getUserId(), 10L);
        notificationService.sendNotification(comment.getQuestion().getUser().getUserId(), authService.getCurrentUser().getUsername() + " has posted a comment on your question titled '" +
                comment.getQuestion().getPostName() + "'.");
        return mapper.mapComment(comment);

    }

    public String upVote(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(commentNotFound + id));
        comment.setUpVoteCount(comment.getUpVoteCount() + 1);
        commentRepository.save(comment);
        return "question with text" + comment.getText() + "upvoted";
    }

    public String downVote(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(commentNotFound + id));
        if (comment.getDownVoteCount() == null) {
            comment.setDownVoteCount(0);
        }
        comment.setDownVoteCount(comment.getDownVoteCount() + 1);
        commentRepository.save(comment);
        return "question with text" + comment.getText() + "downvoted";
    }


    public String selectComment(Long id) {
        Comment c = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(commentNotFound + id));
        if (c.getQuestion().getUser().getUserId().equals(authService.getCurrentUser().getUserId())) {
            return "you dont have the privledge to accept this answer try upvoting it";
        }
        c.setSelected(true);
        commentRepository.save(c);
        userService.incrementUserPoints(c.getUser().getUserId(), 50L);
        notificationService.sendNotification(c.getUser().getUserId(), authService.getCurrentUser().getUsername() + " selected your comment as an acceptable solution" +
                " for the problem id " + c.getQuestion().getPostId() + " and you've been awarded 50 Points");
        return "This comment as selected is the accepted answer";
    }

    public String deleteComment(Long id) {
        Comment c = commentRepository.findById(id).orElseThrow(()-> new RuntimeException(commentNotFound +id));
        if (c.getUser().getUserId().equals(authService.getCurrentUser().getUserId())) {
            return "you dont have the privledge to delete this answer";
        }
        commentRepository.deleteById(c.getId());
        return "This comment is deleted by " + authService.getCurrentUser().getUsername();
    }
}




