package com.telstra.repository;

import com.telstra.model.Spam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpamRepository extends JpaRepository<Spam, Long> {

}
