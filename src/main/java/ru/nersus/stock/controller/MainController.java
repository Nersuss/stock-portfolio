package ru.nersus.stock.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nersus.stock.config.MyUserDetails;
import ru.nersus.stock.dto.AddStockDto;
import ru.nersus.stock.dto.PortfolioDto;
import ru.nersus.stock.dto.api.GlobalQuoteDto;
import ru.nersus.stock.dto.api.StockPricesDto;
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
    String getLanding(@RequestParam(required = false) String symbol,
                      @RequestParam(required = false) String period,
                      Model model) throws IOException {
        if (StringUtils.isEmpty(symbol) || StringUtils.isEmpty(period)) {
            return "redirect:/?symbol=AAPL&period=WEEKLY";
        }

        StockPricesDto stockPricesDto = stockService.getPricesBySymbolAndPeriod(symbol, period);
        GlobalQuoteDto stockInfoDto = stockService.getStockInfoBySymbol(symbol);
        model.addAttribute("stockLabels", stockPricesDto.getLabels());
        model.addAttribute("stockPrices", stockPricesDto.getOpenPrices());
        model.addAttribute("stockInfo", stockInfoDto);
        return "landing";
    }

    @GetMapping("/portfolio")
    String getPortfolio(Principal principal, Model model) throws IOException {
        PortfolioDto portfolio = userService.getPortfolio(principal.getName());
        model.addAttribute("stocks", portfolio.stocks());
        model.addAttribute("portfolioCost", portfolio.cost());
        return "portfolio";
    }

    @GetMapping("/portfolio/edit")
    String getPortfolioEdit() {
        return "portfolio-edit";
    }

    @GetMapping("/stock")
    StockPricesDto getStockPrices(@RequestParam(required = false) String symbol, @RequestParam(required = false) String period) throws IOException {
        StockPricesDto pricesBySymbolAndPeriod = stockService.getPricesBySymbolAndPeriod(symbol, period);

        if (pricesBySymbolAndPeriod != null) {
            return pricesBySymbolAndPeriod;
        }
        throw new NullPointerException();
    }

    @PostMapping("/portfolio/stock/add")
    String addStock(@ModelAttribute("addStockDto") AddStockDto addStockDto, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.addStockByEmail(addStockDto, myUserDetails);
        return "redirect:/portfolio";
    }

}
