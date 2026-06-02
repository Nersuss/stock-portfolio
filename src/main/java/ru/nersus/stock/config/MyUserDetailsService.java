package ru.nersus.stock.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nersus.stock.entity.User;
import ru.nersus.stock.dao.UserDao;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MyUserDetailsService implements UserDetailsService {

    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDao.findByEmail(username);
        if (user.isPresent()) {
            return new MyUserDetails(user.get());
        }
        throw new UsernameNotFoundException("Error authentication: " + username);
    }
}
