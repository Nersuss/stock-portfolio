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
import ru.nersus.stock.dto.api.GlobalQuoteDto;
import ru.nersus.stock.entity.Stock;
import ru.nersus.stock.entity.User;
import ru.nersus.stock.dao.AlphaVantage;
import ru.nersus.stock.dao.StockDao;
import ru.nersus.stock.dao.UserDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserDao userDao;
    StockDao stockDao;
    AlphaVantage alphaVantage;

    public User findUserByEmailAndPassword(LoginDto loginDto) {
        Optional<User> user = userDao.findByEmail(loginDto.getEmail());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    public void addStockByEmail(AddStockDto addStockDto, MyUserDetails myUserDetails) {
        stockDao.addByUserId(
                new Stock(null,
                        addStockDto.symbol(),
                        addStockDto.count(),
                        myUserDetails.getId()
                ));
    }

    public PortfolioDto getPortfolio(String email) throws IOException {
        List<Stock> userStocks = stockDao.getStocksByEmail(email);

        List<StockDto> stockDtos = new ArrayList<>();

        double portfolioCost = 0;
        for (Stock stock : userStocks) {
            GlobalQuoteDto stockInfoBySymbol = alphaVantage.getStockInfoBySymbol(stock.symbol());
            stockDtos.add(new StockDto(stock.id(), stock.symbol(), stockInfoBySymbol.previousClose(), stock.count(), stockInfoBySymbol));
            portfolioCost += stockInfoBySymbol.price() * stock.count();
        }

        return new PortfolioDto(portfolioCost, stockDtos);
    }

    public void deleteStockById(int id, MyUserDetails myUserDetails) {
        stockDao.deleteStockById(id, myUserDetails);
    }
}
