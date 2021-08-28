package com.telstra.service;


import com.telstra.dto.TagDto;
import com.telstra.model.Tag;
import com.telstra.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TagService {

    @Autowired
    TagRepository tagRepository;

    @Transactional
    public Object createTag(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        if (tagRepository.findByName(tagDto.getName()).isPresent()) {
            return tagRepository.findByName(tagDto.getName());
        }
        return tagRepository.save(tag);

    }

    @Transactional
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Transactional
    public Tag getTagById(Long id) {
        return tagRepository.findById(id).get();
    }
}
