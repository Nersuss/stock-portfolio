package ru.nersus.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nersus.stock.dto.LoginDto;
import ru.nersus.stock.jwt.JwtRp;
import ru.nersus.stock.service.AuthService;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Контроллер с методами авторизации", description = "")
public class AuthController {

    AuthService authService;

    @GetMapping("/login")
    @Operation(
            summary = "Получение страницы входа в аккаунт",
            description = "Вывод формы для входа в аккаунт"
    )
    String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    @Operation(summary = "Вход в аккаунт")
    String login(@ModelAttribute("loginDto") LoginDto loginDto, HttpServletResponse response) {
        JwtRp jwtRp = authService.loginUser(loginDto);
        response.addCookie(new Cookie("accessToken", jwtRp.accessToken()));
        response.addCookie(new Cookie("refreshToken", jwtRp.refreshToken()));
        return "redirect:/";
    }

    @GetMapping("/register")
    @Operation(
            summary = "Получение страницы регистрации нового аккаунта",
            description = "Вывод формы для регистрации"
    )
    String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация пользователя на основе введенных данных",
            description = "Метод осуществляет регистрацию пользователя в системе"
    )
    String registration(@ModelAttribute("loginDto") LoginDto loginDto, HttpServletResponse response) {
        JwtRp jwtRp = authService.registerUser(loginDto);
        response.addCookie(new Cookie("accessToken", jwtRp.accessToken()));
        response.addCookie(new Cookie("refreshToken", jwtRp.refreshToken()));
        return "redirect:/login";
    }

}
