package com.librarymanagement.data.repository;

import com.librarymanagement.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
