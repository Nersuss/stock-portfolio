package ru.nersus.stock.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nersus.stock.dao.UserDao;
import ru.nersus.stock.dto.LoginDto;
import ru.nersus.stock.dto.RegisterRqDto;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserDao userDao;

    public void registerUser(LoginDto loginDto) {
        if (userDao.findByEmail(loginDto.getEmail()).isPresent()) {
            System.out.println("User already registered");
            throw new UsernameNotFoundException("User already registered");
        }
        userDao.registerUser(
                new RegisterRqDto(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
    }

}
