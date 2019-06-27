package com.example.demo.service;

import com.example.demo.dto.ListUserDto;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public interface UserService {
	/**
	 * get all student
	 *
	 * @return list student
	 */

	/**
	 *
	 * @param id input student id
	 * @return Student
	 */
	public User findById(long id);

	/**
	 *
	 * @param studentDto input information student
	 * @return return true if add success else return false
	 */
	public boolean add(User user);

	/**
	 *
	 * @return return true if update success else return false
	 */
	public User loadUserByUsername(String username);

	/**
	 *
	 * @param id input id of student
	 * @return return true if delete success else return false
	 */
	public boolean delete(long id);

	public boolean checkLogin(User user);

	public ListUserDto findAll (String search, int offset, int limit);

	public boolean update(User user);

	public boolean chagePass(User user);
}
