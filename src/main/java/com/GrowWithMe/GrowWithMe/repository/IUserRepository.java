package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
}
