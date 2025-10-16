package ru.nersus.stock.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nersus.stock.config.MyUserDetails;
import ru.nersus.stock.dto.AddStockDto;
import ru.nersus.stock.dto.LoginDto;
import ru.nersus.stock.dto.PortfolioDto;
import ru.nersus.stock.dto.StockDto;
import ru.nersus.stock.entity.Stock;
import ru.nersus.stock.entity.User;
import ru.nersus.stock.repo.AlphaVantage;
import ru.nersus.stock.repo.StockRepo;
import ru.nersus.stock.repo.UserRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepo userRepo;
    StockRepo stockRepo;
    AlphaVantage alphaVantage;

    public User findUserByEmailAndPassword(LoginDto loginDto) {
        Optional<User> user = userRepo.findByEmailAndLogin(loginDto.getEmail(), loginDto.getPassword());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    public void addStockByEmail(AddStockDto addStockDto, MyUserDetails myUserDetails) {
        stockRepo.save(
                new Stock(null,
                        addStockDto.symbol(),
                        addStockDto.count(),
                        new User(myUserDetails.getId(),
                                myUserDetails.getUsername(),
                                null)));
    }

    public PortfolioDto getPortfolio(String email) throws IOException {
        List<Stock> stocks = stockRepo.getStockByOwner_Email(email);
        List<StockDto> stockDtos = new ArrayList<>();
        double portfolioCost = 0;
        for (Stock stock : stocks) {
            portfolioCost += alphaVantage.getStockInfoBySymbol(stock.getSymbol()).price();
            stockDtos.add(new StockDto(stock.getSymbol(), 150, stock.getCount()));
        }

        if (stocks.isEmpty()) {
            throw new UsernameNotFoundException("No stocks in portfolio");
        }
        return new PortfolioDto(portfolioCost, stockDtos);
    }

}
