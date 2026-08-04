package ru.nersus.stock.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nersus.stock.dao.UserDao;
import ru.nersus.stock.dto.LoginDto;
import ru.nersus.stock.dto.RegisterRqDto;
import ru.nersus.stock.entity.User;
import ru.nersus.stock.exception.UserAlreadyExistsException;
import ru.nersus.stock.jwt.JwtProvider;
import ru.nersus.stock.jwt.JwtRp;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthService {

    UserDao userDao;
    JwtProvider jwtProvider;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtRp registerUser(LoginDto loginDto) {
        if (userDao.getByEmail(loginDto.email()).isPresent()) {
            log.info("User with email: {} already registered", loginDto.email());
            throw new UserAlreadyExistsException("User already registered");
        }
        String refreshToken = jwtProvider.generateRefreshToken(loginDto.email());
        int id = userDao.registerUser(
                new RegisterRqDto(
                        loginDto.email(),
                        loginDto.password()),
                refreshToken
        );
        String accessToken = jwtProvider.generateAccessToken(loginDto.email(), id);
        log.info("Success register user with email: {}", loginDto.email());
        return new JwtRp(accessToken, refreshToken);
    }

    public JwtRp loginUser(LoginDto loginDto) {
        Optional<User> user = userDao.getByEmail(loginDto.email());
        if (user.isEmpty() || !bCryptPasswordEncoder.matches(loginDto.password(), user.get().password())) {
            log.info("Sign-in error. Incorrect credentials. Email: {}", loginDto.email());
            throw new UsernameNotFoundException("User not found");
        }

        String accessToken = jwtProvider.generateAccessToken(loginDto.email(), user.get().id());
        String refreshToken = jwtProvider.generateRefreshToken(loginDto.email());
        userDao.updateRefreshToken(loginDto.email(), refreshToken);

        return new JwtRp(accessToken, refreshToken);
    }

}
