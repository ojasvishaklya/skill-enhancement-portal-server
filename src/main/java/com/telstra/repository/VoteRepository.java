package com.telstra.repository;

import com.telstra.model.Question;
import com.telstra.model.User;
import com.telstra.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Question post, User currentUser);
}
