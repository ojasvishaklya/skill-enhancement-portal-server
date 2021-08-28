package com.telstra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest {
    private String postName;
    private String url;
    private String description;
    private TagDto tag;
}
