package com.iktpreobuka.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.EUuserRole;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.repositories.UserRepository;

@RestController
@RequestMapping(value = "/project")
public class UserControllers {
	
	@Autowired
	public UserRepository userRepository;
//	• 1.2 u paketu com.iktpreobuka.project.controllers napraviti 
//	klasu UserController sa metodom getDB() koja vraća listu
//	svih korisnika aplikacije
	
//	List<UserEntity> users = new ArrayList<>();
//	protected List<UserEntity> getDB() {
//		if (users.size() == 0) {
//			users.add(new UserEntity(1, "Sasa", "Jovkovic", "joxxa", "sasasa", "joxxasasa@gmail.com", EUuserRole.ROLE_CUSTOMER));
//			users.add(new UserEntity(2, "Marija", "Jovkovic", "cokolino", "mamama", "jovmarija@gmail.com", EUuserRole.ROLE_CUSTOMER));
//		}
//		return users;
//	}
	
//	• 1.3 kreirati REST endpoint koji vraća listu korisnika
//	aplikacije
//	• putanja /project/users
//	@RequestMapping(method = RequestMethod.GET, value = "/users")
//	public List<UserEntity> getUsers() {
//		return getDB();
//	}
	
	@GetMapping("/users")
	public Iterable<UserEntity> getAll() {
		return userRepository.findAll();
	}
	
//	• 1.4 kreirati REST endpoint koji vraća korisnika po 
//	vrednosti prosleđenog ID-a
//	• putanja /project/users/{id}
//	• u slučaju da ne postoji korisnik sa traženom vrednošću ID-a vratiti 
//	null
	
//	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
//	public UserEntity getById(@PathVariable Integer id) {
//		List<UserEntity> users = getDB();
//		for(UserEntity user : users) {
//			if(user.getId().equals(id)) {
//				return user;
//			}
//		}
//		return null;
//	}
	
	@GetMapping("/users/{id}")
	public UserEntity getById(@PathVariable Integer id) {
		return userRepository.findById(id).get();
	}
	
//	• 1.5 kreirati REST endpoint koji omogućava dodavanje 
//	novog korisnika
//	• putanja /project/users
//	• u okviru ove metode postavi vrednost atributa user role na
//	ROLE_CUSTOMER
//	• metoda treba da vrati dodatog korisnika
	//@RequestMapping(method = RequestMethod.POST, value = "/users")
	
//	@PostMapping("/users")
//	public UserEntity addUser(@RequestBody UserEntity newUser) {
//		List<UserEntity> users = getDB();
//		newUser.setId((new Random()).nextInt());
//		users.add(newUser);
//		return newUser;
//	}
	
	@PostMapping("/users")
	public UserEntity addUser(@RequestBody UserEntity newUser) {
		userRepository.save(newUser);
		return newUser;
	}
	
//	• 1.6 kreirati REST endpoint koji omogućava izmenu 
//	postojećeg korisnika
//	• putanja /project/users/{id}
//	• ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda 
//	treba da vrati null, a u suprotnom vraća podatke korisnika sa 
//	izmenjenim vrednostima
//	• NAPOMENA: u okviru ove metode ne menjati vrednost atributa
//	user role i password
	
//	@PutMapping("/users/{id}")
//	public UserEntity changeUser(@PathVariable Integer id, @RequestBody UserEntity changedUser) {
//		for(UserEntity user : getDB()) {
//			if(user.getId().equals(id)) {
//				if(user.getName() != null)
//				user.setName(changedUser.getName());
//				if(user.getLastname() != null)
//				user.setLastname(changedUser.getLastname());
//				if(user.getEmail() != null)
//				user.setEmail(changedUser.getEmail());
//				if(user.getUsername() != null)
//				user.setUsername(changedUser.getUsername());
//				return user;
//			}
//		}
//		return null;
//	}
	
	@PutMapping("/users/{id}")// zbog cega dodaje usera pri promeni
	public UserEntity changeUser(@PathVariable Integer id, @RequestBody UserEntity changedUser) {
		if (userRepository.existsById(id)) {
			UserEntity user = userRepository.findById(id).get();
			if (user.getName() != null)
				user.setName(changedUser.getName());
			if (user.getLastname() != null)
				user.setLastname(changedUser.getLastname());
			if (user.getEmail() != null)
				user.setEmail(changedUser.getEmail());
			if (user.getUsername() != null)
				user.setUsername(changedUser.getUsername());
			userRepository.save(changedUser);
			return changedUser;
		}
		return null;
	}
	
//	• 1.7 kreirati REST endpoint koji omogućava izmenu 
//	atributa user_role postojećeg korisnika
//	• putanja /project/users/change/{id}/role/{role}
//	• ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda 
//	treba da vrati null, a u suprotnom vraća podatke korisnika sa 
//	izmenjenom vrednošću atributa user role
	
//	@RequestMapping(method = RequestMethod.PUT, value = "/users/change/{id}/role/{role}")
//	public UserEntity changeRole(@PathVariable Integer id, @PathVariable EUuserRole role) {
//		for(UserEntity user : getDB()) {
//			if(user.getId().equals(id)) {
//				user.setUserRole(role);
//				return user;
//			}
//		}
//		return null;
//	}
	
	@PutMapping("/users/change/{id}/role/{role}")
	public UserEntity changeRole(@PathVariable Integer id, @PathVariable EUuserRole role) {
		if(userRepository.existsById(id)) {
			UserEntity user = userRepository.findById(id).get();
			user.setUserRole(role);
			return userRepository.save(user);
		} else
		return null;
	}
	
	
//	• 1.8 kreirati REST endpoint koji omogućava izmenu 
//	vrednosti atributa password postojećeg korisnika
//	• putanja /project/users/changePassword/{id}
//	• kao RequestParam proslediti vrednosti stare i nove lozinke
//	• ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda 
//	treba da vrati null, a u suprotnom vraća podatke korisnika sa 
//	izmenjenom vrednošću atributa password
//	• NAPOMENA: da bi vrednost atributa password mogla da bude
//	zamenjena sa novom vrednošću, neophodno je da se vrednost
//	stare lozinke korisnika poklapa sa vrednošću stare lozinke
//	prosleđene kao RequestParam
	
//	@RequestMapping(method = RequestMethod.PUT, value = "/users/changePassword/{id}")
//	public UserEntity changePassword(@PathVariable Integer id, @RequestParam String oldPass, @RequestParam String newPass) {
//		for(UserEntity user : getDB()) {
//			if(user.getId().equals(id) && user.getPassword().equals(oldPass)) {
//				user.setPassword(newPass);
//				return user;
//			}
//		}
//		return null;
//	}
	
	@PutMapping("/users/changePassword/{id}")// ne menja password i ne daje nikakvu gresku
	public UserEntity changePassword(@PathVariable Integer id, @RequestParam String oldPass, @RequestParam String newPass) {
		if(userRepository.existsById(id)) {
			UserEntity user = userRepository.findById(id).get();
			if(user.getPassword().equals(oldPass)) {
				user.setPassword(newPass);
				return userRepository.save(user);
			}
		} 
		return null;
	}


	
//	• 1.9 kreirati REST endpoint koji omogućava brisanje 
//	postojećeg korisnika
//	• putanja /project/users/{id}
//	• ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda 
//	treba da vrati null, a u suprotnom vraća podatke o korisniku koji je 
//	obrisan
	
//	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}") 
//	public UserEntity deleteUser(@PathVariable Integer id) {
//		List<UserEntity> users = getDB();
//		Iterator<UserEntity> it = users.iterator();
//		while(it.hasNext()) {
//			UserEntity user = it.next();
//			if(user.getId().equals(id));
//			it.remove();
//			return user;
//		}
//		for(UserEntity user : users) {
//			if(user.getId().equals(id)) {
//				users.remove(user);				NIJE DOBRA PRAKSA
//				return user;
//			}
//		}
//		return null;
//	}
	
	@DeleteMapping("/users/{id}")
	public UserEntity delUser(@PathVariable Integer id) {
		if(userRepository.existsById(id)) {
			UserEntity user = userRepository.findById(id).get();
			userRepository.delete(user);
			return user;
		}
		return null;
	}
	
//	• 1.10 kreirati REST endpoint koji vraća korisnika po 
//	vrednosti prosleđenog username-a
//	• putanja /project/users/by-username/{username}
//	• u slučaju da ne postoji korisnik sa traženim username-om vratiti null
	
//	@RequestMapping(method = RequestMethod.GET, value = "/users/by-username/{username}")
//	public UserEntity findByUsername(@PathVariable String username) {
//		for(UserEntity user : getDB()) {
//			if(user.getUsername().equals(username)) {
//				return user;
//			}
//		}
//		return null;
//	}
	
	@GetMapping("/users/by-username/{username}")
	public UserEntity getByUsername(@PathVariable String username) {
		return userRepository.findByUsername(username);
	}
}
