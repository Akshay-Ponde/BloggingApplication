package com.blogging.repositories;


import com.blogging.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User , Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
