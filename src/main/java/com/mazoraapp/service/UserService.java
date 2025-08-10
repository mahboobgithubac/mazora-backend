package com.mazoraapp.service;
import java.util.List;
import com.mazoraapp.model.User;
//import com.mazoraapp.service.impl.UserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
public interface UserService extends UserDetailsService {
    User registerUser(User user);
    User login(String email, String password);
    List<User> getAllUsers();
    User getUserById(Long id);
	UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
