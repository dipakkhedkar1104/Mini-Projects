package com.myproject.fullstackbackend.repository;

import com.myproject.fullstackbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	

}
