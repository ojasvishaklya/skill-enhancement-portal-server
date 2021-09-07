package com.telstra.controller;


import com.telstra.service.SpamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SpamController {
    @Autowired
    SpamService spamService;

    @PostMapping("/spam/{sId}")
    public String reportSpam(@PathVariable Long sId) {
        return spamService.reportSpam(sId);
    }

    @GetMapping("/spam/{id}")
    public List<Long> getSpam(@PathVariable Long id) {
        return spamService.getSpam(id);
    }
}
