package com.example.demo.repository;


import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	@Query("select new com.example.demo.model.User(cs) from User cs where cs.userName like ?1")
	User findUserByUserName(String name);

	@Query("select new com.example.demo.model.User(cs) from User cs where cs.userName like ?1 and cs.password like ?2")
	User checkLogin(String username, String password);

	@Query("select new com.example.demo.model.User(cs) from User cs where cs.userName like %?1% or cs.hovaten like %?1% ")
	User findAll(String search, String offset, String limit);
}

