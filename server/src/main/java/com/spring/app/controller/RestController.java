package com.spring.app.controller;

import java.sql.SQLDataException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.spring.app.model.Place;
import com.spring.app.model.Post;
import com.spring.app.model.UserInfo;
import com.spring.app.services.ChangeUserDetailsService;
import com.spring.app.services.PlacesService;
import com.spring.app.services.SearchPostService;
import com.spring.app.services.SharePostService;
import com.spring.app.services.UserServiceImpl;

@org.springframework.web.bind.annotation.RestController
@Configuration
@ComponentScan("com.spring")
public class RestController {

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	SharePostService sharePostService;

	@Autowired
	SearchPostService searchPostService;

	@Autowired
	private HttpServletRequest context;

	@Autowired
	PlacesService placesService;

	@Autowired
	ChangeUserDetailsService changeUserDetalsService;

	@RequestMapping("/register")
	@ExceptionHandler({ SQLDataException.class })
	public @ResponseBody String registeUser(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			@RequestParam("phonenumber") String phonenumber) {
		UserInfo user = new UserInfo();
		user.setEmail(email);
		user.setPassword(password);
		user.setPhonenumber(phonenumber);
		user.setUsername(username);

		userServiceImpl.registerUser(user);
		return "Done!";
	}

	@RequestMapping(value = "/home/userDetails", method = RequestMethod.GET, produces = "application/json")
	public UserInfo getUserDetails(@RequestParam("email") String email) {
		return userServiceImpl.getUserDetailsByEmail(email);
	}

	@RequestMapping(value = "/home/post/getAllPlaces", method = RequestMethod.GET, produces = "application/json")
	public List<Place> getAllPlaces() {
		return placesService.getAllPlaces();
	}

	@RequestMapping(value = "/home/post/share", method = RequestMethod.POST)
	public void sharePost(@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("phonenumber") String phonenumber,
			@RequestParam("startDest") String startDest,
			@RequestParam("endDest") String endDest,
			@RequestParam("startTime") String startTime,
			@RequestParam("startDate") String startDate,
			@RequestParam("desc") String desc) {
		Post post = new Post();
		post.setDate(startDate);
		post.setTimeStart(startTime);
		post.setDescription(desc);
		post.setStartDestination(startDest);
		post.setEndDestination(endDest);

		UserInfo user = new UserInfo();
		user.setEmail(email);
		user.setPhonenumber(phonenumber);
		user.setUsername(username);
		post.setUserInfo(user);

		sharePostService.sharePost(post);
	}

	@RequestMapping(value = "/home/search/findPosts", method = RequestMethod.GET, produces = "application/json")
	public List<Post> findPosts(@RequestParam("startDest") String startDest,
			@RequestParam("endDest") String endDest,
			@RequestParam("startDate") String startDate) {
		return searchPostService.findPosts(startDest, endDest, startDate);
	}

	@RequestMapping(value = "/register/checkMail", method = RequestMethod.GET, produces = "application/json")
	public int checkMail(@RequestParam("email") String email) {
		return changeUserDetalsService.checkMail(email);
	}

	@RequestMapping(value = "/home/user/checkPassword", method = RequestMethod.GET, produces = "application/json")
	public int checkPassword(@RequestParam("email") String email,
			@RequestParam("oldPass") String oldPass) {
		return changeUserDetalsService.checkPassword(email, oldPass);
	}

	@RequestMapping(value = "/home/user/changePassword", method = RequestMethod.POST, produces = "application/json")
	public void changePassword(@RequestParam("email") String email,
			@RequestParam("newPass") String newPass) {
		changeUserDetalsService.changePasswordByEmail(email, newPass);
	}

	@RequestMapping(value = "/home/user/changePhonenumber", method = RequestMethod.POST, produces = "application/json")
	public void changePhonenumber(@RequestParam("email") String email,
			@RequestParam("phonenumber") String phonenumber) {
		changeUserDetalsService.chagePhonenumberByEmail(email, phonenumber);
	}

	@RequestMapping(value = "/home/search/deletePost", method = RequestMethod.POST, produces = "application/json")
	public void deletePost(@RequestParam("id") int id) {
		searchPostService.deletePost(id);
	}

	@RequestMapping(value = "/home/search/findMyActivePosts", method = RequestMethod.GET, produces = "application/json")
	public List<Post> findMyActivePosts(@RequestParam("email") String email) {
		return searchPostService.findPostsByEmail(email);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@RequestMapping(value = "/bad_request_error", produces = "application/json")
	public String unauhorized() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		System.out.println(auth.isAuthenticated());
		return new String("UNAUTHORIZED");
	}

}
