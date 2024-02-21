package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserLogin(String userLogin);
    @Query("SELECT t.trainerId FROM Trainer t WHERE t.user.userId = :userId")
    Integer findTrainerIdByUserId(@Param("userId") Integer userId);

    @Query("SELECT c.clientId FROM Client c WHERE c.user.userId = :userId")
    Integer findClientIdByUserId(@Param("userId") Integer userId);
}
