package ru.nersus.stock.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nersus.stock.dto.LoginDto;
import ru.nersus.stock.dto.LoginRpDto;
import ru.nersus.stock.dto.RegisterRqDto;
import ru.nersus.stock.entity.User;
import ru.nersus.stock.repo.StockRepo;
import ru.nersus.stock.repo.UserDao;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserDao userDao;
    //StockRepo stockRepo;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginRpDto registerUser(LoginDto loginDto) {
        if (userDao.findByEmail(loginDto.getEmail()).isPresent()) {
            System.out.println("User already registered");
            throw new UsernameNotFoundException("User already registered");
        }
        loginDto.setPassword(bCryptPasswordEncoder.encode(loginDto.getPassword()));
        userDao.registerUser(
                new RegisterRqDto(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        return new LoginRpDto("aksjjkh3o4h238983dj3io9d30");
    }

//    public User findUserByEmailAndPassword(LoginDto loginDto) {
//        Optional<User> user = userDao.findByEmailAndLogin(loginDto.getEmail(), loginDto.getPassword());
//        if (user.isEmpty()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return user.get();
//    }

}
