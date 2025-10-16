package ru.nersus.stock.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nersus.stock.dto.LoginDto;
import ru.nersus.stock.dto.LoginRpDto;
import ru.nersus.stock.entity.User;
import ru.nersus.stock.repo.StockRepo;
import ru.nersus.stock.repo.UserRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserRepo userRepo;
    StockRepo stockRepo;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginRpDto registerUser(LoginDto loginDto) {
        if (userRepo.existsByEmail(loginDto.getEmail())) {
            System.out.println("User already registered");
            throw new UsernameNotFoundException("User already registered");
        }
        loginDto.setPassword(bCryptPasswordEncoder.encode(loginDto.getPassword()));
        userRepo.save(
                new User(null,
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        return new LoginRpDto("aksjjkh3o4h238983dj3io9d30");
    }

    public User findUserByEmailAndPassword(LoginDto loginDto) {
        Optional<User> user = userRepo.findByEmailAndLogin(loginDto.getEmail(), loginDto.getPassword());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

}
