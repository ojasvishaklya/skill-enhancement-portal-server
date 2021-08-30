package com.telstra.repository;


import com.telstra.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Modifying
    @Transactional
    @Query(value = "SELECT * from `skill-enhancement-portal`.question where match(post_name) against (?1 in boolean mode)", nativeQuery = true)
    public List<Question> searchQuestion(String query);

}
