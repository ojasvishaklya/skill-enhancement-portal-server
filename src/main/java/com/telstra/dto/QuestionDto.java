package com.telstra.dto;

import com.telstra.model.Tag;
import com.telstra.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private String postName;
    private String url;
    private String description;
    private TagDto tag;
}
