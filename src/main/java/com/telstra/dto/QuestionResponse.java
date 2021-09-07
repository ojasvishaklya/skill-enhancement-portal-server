package com.telstra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {
    private Long id;
    private String postName;
    private String url;
    private String description;
    private String tag;
    private List<CommentResponse> comments = new ArrayList<>();
    private int upvotes;
    private int downvotes;
    private String creator;
}
