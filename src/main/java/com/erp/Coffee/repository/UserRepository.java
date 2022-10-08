package com.erp.Coffee.repository;

import com.erp.Coffee.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Transactional
    @Query(value = "SELECT * FROM user WHERE role = ?1", nativeQuery = true)
    Optional<List<User>> findAllBaristas(String role);
}
