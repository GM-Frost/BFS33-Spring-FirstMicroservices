package com.microservice.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.userservice.model.User;
import com.microservice.userservice.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserRepository userRepository;
	
	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/")
	public List<User>getAllUser(){
		return userRepository.findAll();
	}
	
	@PostMapping("/")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	
	@PutMapping("/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User updateUser) {
		User existingUser = userRepository.findById(id).orElse(null);
		
		if(existingUser!=null) {
			existingUser.setUsername(updateUser.getUsername());
			existingUser.setEmail(updateUser.getEmail());
		}
		return null;
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		userRepository.deleteById(id);
	}
	
	//CONNECTING WITH MICROSERVICES
	@Autowired
    private RestTemplate restTemplate;
	
    @GetMapping("/{userId}/products")
    public List<Object> getProductsForUser(@PathVariable Long userId) {
    
        String productServiceUrl = "http://localhost:8080/api/products/";         

    	ResponseEntity<List<Object>> response = restTemplate.exchange(
    	                productServiceUrl,
    	                HttpMethod.GET,
    	                null,
    	                new ParameterizedTypeReference<List<Object>>() {}
    	        );

    	        return response.getBody();
    	    }
    	
	
}
