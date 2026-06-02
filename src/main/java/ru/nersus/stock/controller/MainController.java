package ru.nersus.stock.controller;

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
import ru.nersus.stock.config.MyUserDetails;
import ru.nersus.stock.dto.AddStockDto;
import ru.nersus.stock.dto.LandingDto;
import ru.nersus.stock.dto.PortfolioDto;
import ru.nersus.stock.service.StockService;
import ru.nersus.stock.service.UserService;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainController {

    StockService stockService;
    UserService userService;

    @GetMapping("/")
    String getLanding(@RequestParam(required = false) String symbol, @RequestParam(required = false) String period,
                      Model model) throws IOException {
        if (StringUtils.isEmpty(symbol) || StringUtils.isEmpty(period)) {
            return "redirect:/?symbol=AAPL&period=WEEKLY";
        }

        LandingDto landingDto = stockService.getLanding(symbol);
        model.addAttribute("landing", landingDto);
        return "landing";
    }

    @GetMapping("/portfolio")
    String getPortfolio(Principal principal, Model model) throws IOException {
        PortfolioDto portfolio = userService.getPortfolio(principal.getName());
        model.addAttribute("portfolio", portfolio);
        return "portfolio";
    }

    @GetMapping("/portfolio/edit")
    String getPortfolioEdit(Principal principal, Model model) throws IOException {
        PortfolioDto portfolio = userService.getPortfolio(principal.getName());
        model.addAttribute("portfolio", portfolio);
        return "portfolio-edit";
    }

    @PostMapping("/portfolio/stock/add")
    String addStock(@ModelAttribute("addStockDto") AddStockDto addStockDto, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        userService.addStockByEmail(addStockDto, myUserDetails);
        return "redirect:/portfolio";
    }

    @PostMapping("/portfolio/stock/delete")
    String deleteStock(@RequestParam int id, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        userService.deleteStockById(id, myUserDetails);
        return "redirect:/portfolio/edit";
    }

}
