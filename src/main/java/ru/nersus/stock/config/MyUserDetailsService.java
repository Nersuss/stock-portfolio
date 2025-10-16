package ru.nersus.stock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nersus.stock.entity.User;
import ru.nersus.stock.repo.UserRepo;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmail(username);
        if (user.isPresent()) {
            return new MyUserDetails(user.get());
        }
        throw new UsernameNotFoundException("Error authentication: " + username);
    }
}
