package com.telstra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String text;
    private String url;
    private String instant;
    private boolean selected;
    private int upvotes;
    private int downvotes;
    private String creator;
    private String creatorId;
}
