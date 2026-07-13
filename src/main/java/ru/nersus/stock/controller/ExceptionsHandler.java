package ru.nersus.stock.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.nersus.stock.exception.UserAlreadyExistsException;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String UsernameNotFoundException(HttpServletRequest request, Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());

        return "login";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String UserAlreadyExistsException(HttpServletRequest request, Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        //model.addAttribute("targetUrl", request.getRequestURI());

        return "register";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String IllegalArgumentException(HttpServletRequest request, Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());

        return "redirect:/";
    }

}
