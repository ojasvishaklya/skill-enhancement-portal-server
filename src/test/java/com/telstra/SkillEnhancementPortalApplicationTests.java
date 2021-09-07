package com.telstra;

import com.telstra.model.User;
import com.telstra.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SkillEnhancementPortalApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    @Order(1)
    public void testFindByUsername () {
        String username="ojasvi";
        User p = userRepository.findByUsername(username).get();
        assertEquals(username,p.getUsername());
    }

    @Test
    @Order(2)
    public void testReadAll () {
        List list = userRepository.findAll();
        assertEquals(true,(list).size()>(0));
    }

    @Test
    @Order(3)
    public void testFindById () {
        User u = userRepository.findById(1L).get();
        assertEquals("ojasvishaklya@gmail.com", u.getEmail());
    }

}
