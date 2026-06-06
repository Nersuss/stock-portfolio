package ru.nersus.stock.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nersus.stock.dto.LoginDto;
import ru.nersus.stock.service.AuthService;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @GetMapping("/login")
    String getLogin() {
        return "login";
    }

    @GetMapping("/register")
    String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    String registration(@ModelAttribute("loginDto") LoginDto loginDto) {
        authService.registerUser(loginDto);
        return "redirect:/login";
    }

}
