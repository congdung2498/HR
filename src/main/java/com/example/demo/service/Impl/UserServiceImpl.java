package com.example.demo.service.Impl;


import com.example.demo.dto.ListUserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

//	static {
//		User userKai = new User(1, "kai", "123456");
//		userKai.setRoles(new String[] { "ROLE_ADMIN" });
//
//		User userSena = new User(2, "sena", "123456");
//		userSena.setRoles(new String[] { "ROLE_USER" });
//
//		listUser.add(userKai);
//		listUser.add(userSena);
//	}

	@Override
	public User findById(long id) {
		User user;
		user = userRepository.findOne(id);
		return user;
	}

	@Override
	public boolean add(User user) {
		User user1;
		user1 = userRepository.findUserByUserName(user.getUserName());
		if (user1 == null){
			String encodedString = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
			user.setPassword(encodedString);
			userRepository.save(user);
			return true;
		}
		else return false;
	}

	@Override
	public boolean delete(long id) {
		User user;
		user = userRepository.findOne(id);
		if(user != null){
			userRepository.delete(id);
			return true;
		}
		return false;
	}

	@Override
	public User loadUserByUsername(String username) {
		User user;
		user = userRepository.findUserByUserName(username);
		if(user !=null){
			return user;
		}
		else return null;
	}

	@Override
	public boolean checkLogin(User user) {
		User user1;
		String encodedString = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
		user1 = userRepository.checkLogin(user.getUserName(), encodedString);
//		System.out.println(user.getUsername() +" "+ user.getPassword());
		if(user1 != null){
			return true;
		}
		return false;
	}

	@Override
	public ListUserDto findAll(String search, int offset, int limit) {
		ArrayList<User> users;
		ListUserDto listDto = new ListUserDto();
//		users = userRepository.findAll(search,offset,limit);
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session =  sessionFactory.openSession();
		Query query = session.createQuery("select new com.example.demo.model.User(cs) from User cs where cs.userName like :search or cs.hovaten like :search ").setParameter("search", "%" + search + "%");
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		users = (ArrayList)query.list();

		Query query2 = session.createQuery("select count(*) from User gd");
		Iterator count = query2.iterate();
		long total = (long)count.next();
		if(users !=null){
			listDto.setRows(users);
			listDto.setTotal(total);
			listDto.setTotalNotFiltered(total);
			return listDto;
		}
		else return null;
	}

	@Override
	public boolean update(User us) {
		User user;
		System.out.println(us.getHovaten());
		if(us.getId()!=0){
			user = userRepository.findOne(us.getId());
		}
		else return false;
		if(user != null){
			user.setHovaten(us.getHovaten());
			user.setRole(us.getRole());
			userRepository.save(user);
			return true;
		}
		else return false;
	}

	@Override
	public boolean chagePass(User us) {
		User user;
		user = userRepository.findOne(us.getId());
		if(user ==null){
			return false;
		}
		else {
			String encodedString = Base64.getEncoder().encodeToString(us.getPassword().getBytes());
			user.setPassword(encodedString);
			userRepository.save(user);
			return true;
		}
	}

}
