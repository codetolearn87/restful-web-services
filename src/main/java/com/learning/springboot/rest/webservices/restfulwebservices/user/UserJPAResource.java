package com.learning.springboot.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAResource {
	
	@Autowired
	private UserDaoService service;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	//GET	/users
	//retrieveAllUsers
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}
	
	//GET	/users/{id}
	//retrieveUser(int id)
	@GetMapping("/jpa/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent())
			throw new UserNotFoundException("id-" + id);
			
		//"all-users", SERVER_PATH + "/users"
		//retrieveAllUsers
		Resource<User> resource = new Resource<User>(user.get());
		
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		resource.add(linkTo.withRel("all-users"));
		
		//HATEOAS
		
		return resource;
		
	}
	
	//POST	/users
	// input - details of user
	// output - CREATED & Return the created URI
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		
		// CREATED
		// /users/4
		// /users/{id}	savedUser.getId()
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	//DELETE	/users/{id}
	//deleteUser(int id)
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);	
	}
	
	//Get post for a particular user
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllUsers(@PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		
		return userOptional.get().getPosts();
		
	}
	
	//Make a post for a particular user
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		
		User user = userOptional.get();
		
		post.setUser(user);
		
		postRepository.save(post);
		
		// CREATED
		// /jpa/users/1/posts
		// /jpa/users/1/posts/{id}	post.getId()
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}