package com.telstra.controller;


import com.telstra.dto.TagDto;
import com.telstra.model.Tag;
import com.telstra.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagsController {
    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public List<Tag> getAll() {
        return tagService.getAll();
    }

    @PostMapping("/tags")
    public Tag createTag(@RequestBody TagDto tag) {
        return tagService.createTag(tag);
    }

    @GetMapping("/tags/{id}")
    public Tag getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }
}
