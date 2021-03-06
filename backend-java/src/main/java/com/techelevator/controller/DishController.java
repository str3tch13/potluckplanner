package com.techelevator.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.dao.DishDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.model.CreateDishDTO;
import com.techelevator.model.Dish;


@PreAuthorize("isAuthenticated()")
@RestController
@CrossOrigin
public class DishController {
	private DishDAO dishDAO;
	private UserDAO userDAO;
	
	public DishController(DishDAO dishDAO, UserDAO userDAO) {
		this.dishDAO = dishDAO;
		this.userDAO = userDAO;
	}
    
    @ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = "/potluck/{id}/dish", method = RequestMethod.POST)
	public void createDish(@Valid @RequestBody CreateDishDTO newDish, Principal principal) {
		long user_id = getCurrentUserId(principal);
		int user_id_int = (int)user_id;
		newDish.setUser_id(user_id_int);
		dishDAO.create(newDish, user_id);
	}
    
    public Long getCurrentUserId(Principal principal) {
		return userDAO.findByUsername(principal.getName()).getId();
	}
    
    @RequestMapping(value = "/potluck/{id}/dish", method = RequestMethod.GET)
	public List<Dish> getDishes(@PathVariable ("id") int potluck_id) {
		return dishDAO.getDishes(potluck_id);
	}
}
