package com.telstra;

import com.telstra.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SkillEnhancementPortalApplicationTests {

    @Autowired
    UserRepository userRepository;

//    @Test
//    @Order(1)
//    public void testFindByUsername() {
//        String username = "ojasvi";
//        User p = userRepository.findByUsername(username).get();
//        assertEquals(username, p.getUsername());
//    }
//
//    @Test
//    @Order(2)
//    public void testReadAll() {
//        List list = userRepository.findAll();
//        assertEquals(true, (list).size() > (0));
//    }
//
//    @Test
//    @Order(3)
//    public void testFindById() {
//        User u = userRepository.findById(1L).get();
//        assertEquals("ojasvishaklya@gmail.com", u.getEmail());
//    }
//
//    @Autowired
//    TagRepository tagRepository;
//
//    @Test
//    @Order(4)
//    public void testFindByTagId() {
//        Long tag = 4L;
//        Tag t = tagRepository.findById(tag).get();
//        assertNotNull(t.getName());
//
//
//    }
//
//    @Test
//    @Order(5)
//    public void testReadAllTag() {
//        List list = tagRepository.findAll();
//        assertEquals(true, (list).size() > (0));
//    }
//
//    @Autowired
//    QuestionRepository questionRepository;
//
//    @Test
//    @Order(6)
//    public void testReadAllQuestion() {
//        List list = questionRepository.findAll();
//        assertEquals(true, (list).size() > (0));
//    }
//
//    @Test
//    @Order(7)
//    public void testFindByQuestionId() {
//        Question question = questionRepository.findById(4L).get();
//    }
}
