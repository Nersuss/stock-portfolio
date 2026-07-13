package ru.nersus.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nersus.stock.dto.AddStockDto;
import ru.nersus.stock.dto.LandingDto;
import ru.nersus.stock.dto.PortfolioDto;
import ru.nersus.stock.enums.PeriodEnum;
import ru.nersus.stock.jwt.JwtAuthentication;
import ru.nersus.stock.service.StockService;
import ru.nersus.stock.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Основной контроллер", description = "")
public class MainController {

    StockService stockService;
    UserService userService;

    @GetMapping("/")
    @Operation(
            summary = "Получение страницы лендинга",
            description = "Вывод основной информации об акции и результатов технического анализа"
    )
    String getLanding(@RequestParam(required = false) String symbol, @RequestParam(required = false) PeriodEnum period,
                      Model model) {
        if (StringUtils.isEmpty(symbol) || period == null) {
            return "redirect:/?symbol=AFLT&period=month";
        }

        LandingDto landingDto = stockService.getLanding(symbol,period);
        model.addAttribute("landing", landingDto);
        return "landing";
    }

    @GetMapping("/portfolio")
    @Operation(
            summary = "Получение страницы портфеля акций пользователя",
            description = "Вывод содержимого портфеля акций и результатов технического анализа"
    )
    String getPortfolio(Principal principal, Model model, PeriodEnum period) {
        if (period == null) {
            return "redirect:/portfolio?period=month";
        }
        PortfolioDto portfolio = userService.getPortfolio(principal.getName(), period);
        model.addAttribute("portfolio", portfolio);
        return "portfolio";
    }

    @GetMapping("/portfolio/edit")
    @Operation(
            summary = "Получение страницы редактирования портфеля акций",
            description = "Вывод содержимого портфеля акций и предоставление возможности для добавления и удаления акций"
    )
    String getPortfolioEdit(Principal principal, Model model) {
        PortfolioDto portfolio = userService.getPortfolio(principal.getName(), null);
        model.addAttribute("portfolio", portfolio);
        return "portfolio-edit";
    }

    @PostMapping("/portfolio/stock/add")
    @Operation(
            summary = "Добавление акции в портфель",
            description = "Метод позволяет добавить выбранную акцию в портфель пользователя"
    )
    String addStock(@ModelAttribute("addStockDto") AddStockDto addStockDto, @AuthenticationPrincipal JwtAuthentication myUserDetails) {
        userService.addStockByEmail(addStockDto, myUserDetails);
        return "redirect:/portfolio";
    }

    @PostMapping("/portfolio/stock/delete")
    @Operation(
            summary = "Удаление акции из портфеля",
            description = "Метод удаляет выбранную акцию из портфеля"
    )
    String deleteStock(@RequestParam int id, @AuthenticationPrincipal JwtAuthentication myUserDetails) {
        userService.deleteStockById(id, myUserDetails);
        return "redirect:/portfolio/edit";
    }

}
