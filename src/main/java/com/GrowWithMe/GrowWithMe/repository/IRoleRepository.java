package com.GrowWithMe.GrowWithMe.repository;

import com.GrowWithMe.GrowWithMe.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByAuthority(String authority);

    Boolean existsByAuthority(String authority);
}
