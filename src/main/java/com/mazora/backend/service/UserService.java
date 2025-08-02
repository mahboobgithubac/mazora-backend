package com.mazora.backend.service;
import java.util.List;
import com.mazora.backend.model.User;
//import com.mazora.backend.service.impl.UserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import com.mazora.backend.service.impl.UsernameNotFoundException;

 public interface UserService extends UserDetailsService {
    User registerUser(User user);
    User login(String email, String password);
    List<User> getAllUsers();
    User getUserById(Long id);
	UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
