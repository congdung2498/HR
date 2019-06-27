package com.example.demo.controller;

import com.example.demo.dto.ListUserDto;
import com.example.demo.dto.UsersDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/rest")
public class UserRestController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;


	/* ---------------- GET ALL USER ------------------------ */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<ListUserDto> getAllUser(@RequestParam String search,@RequestParam String order,@RequestParam int offset,@RequestParam int limit) {
		return new ResponseEntity<ListUserDto>(userService.findAll(search,offset,limit), HttpStatus.OK);
	}

	/* ---------------- GET USER BY ID ------------------------ */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserById(@PathVariable int id) {
		User user = userService.findById(id);
		if (user != null) {
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
	}

	/* ---------------- CREATE NEW USER ------------------------ */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity createUser(@RequestBody User user) {
		if (userService.add(user)) {
			return ResponseEntity.ok(true);
		} else {
			return new ResponseEntity<String>("User Existed!", HttpStatus.BAD_REQUEST);
		}
	}
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public ResponseEntity updateUser(@RequestBody User user) {
		if (userService.update(user)) {
			return ResponseEntity.ok(true);
		} else {
			return new ResponseEntity<String>("false", HttpStatus.BAD_REQUEST);
		}
	}

	/* ---------------- DELETE USER ------------------------ */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteUserById(@PathVariable int id) {
		userService.delete(id);
		return ResponseEntity.ok(true);
	}
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/changePass", method = RequestMethod.POST)
	public ResponseEntity chagePass(@RequestBody User user, Authentication authentication) {

		User u1;
		u1 = userService.findById(user.getId());
		user.setUserName(u1.getUserName());
		Collection<? extends GrantedAuthority> authorities
				= authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities){
			if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
				if(!user.getUserName().equals(authentication.getName())){
					return new ResponseEntity<String>("UnAuthorized", HttpStatus.FORBIDDEN);
				}

			}
		}
		if (userService.chagePass(user)) {
			return ResponseEntity.ok(true);
		} else {
			return new ResponseEntity<String>("false", HttpStatus.BAD_REQUEST);
		}
	}
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public ResponseEntity<String> login(HttpServletRequest request, @RequestBody User user) {
//		System.out.println(user.getUserName()+" "+user.getPassword());
//		String result = "";
//		HttpStatus httpStatus = null;
//		try {
//			if (userService.checkLogin(user)) {
//				result = jwtService.generateTokenLogin(user.getUserName());
//				httpStatus = HttpStatus.OK;
//			} else {
//				result = "Wrong userId and password";
//				httpStatus = HttpStatus.BAD_REQUEST;
//			}
//		} catch (Exception ex) {
//			result = "Server Error";
//			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		return new ResponseEntity<String>(result, httpStatus);
//	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody User user){
		String result = "";
		HttpStatus httpStatus = null;
		try {
			if (userService.checkLogin(user)) {
				result = jwtService.generateTokenLogin(user.getUserName());
				User us = userService.loadUserByUsername(user.getUserName());
				UsersDto usersDto = new UsersDto(us);
				usersDto.setToken(result);
				httpStatus = HttpStatus.OK;
				return ResponseEntity.ok(usersDto);
			} else {
				result = "Wrong userId and password";
				httpStatus = HttpStatus.BAD_REQUEST;
				return new ResponseEntity<String>(result, httpStatus);
			}
		} catch (Exception ex) {
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(result, httpStatus);
	}

}
