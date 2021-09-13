package com.telstra.repository;

import com.telstra.model.Question;
import com.telstra.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = "SELECT * from `skill-enhancement-portal`.user where match(username) against (?1 in boolean mode)", nativeQuery = true)
    public List<User> findByUsername(String query);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
