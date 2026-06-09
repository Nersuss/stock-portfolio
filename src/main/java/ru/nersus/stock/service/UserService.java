package ru.nersus.stock.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nersus.stock.calculation.IndicatorPredict;
import ru.nersus.stock.calculation.TechIndicatorsResults;
import ru.nersus.stock.config.MyUserDetails;
import ru.nersus.stock.dao.MoexApi;
import ru.nersus.stock.dao.StockDao;
import ru.nersus.stock.dao.UserDao;
import ru.nersus.stock.dto.AddStockDto;
import ru.nersus.stock.dto.LoginDto;
import ru.nersus.stock.dto.PortfolioDto;
import ru.nersus.stock.dto.StockDto;
import ru.nersus.stock.dto.api.Candle;
import ru.nersus.stock.dto.api.SecurityDescription;
import ru.nersus.stock.dto.api.StockPrice;
import ru.nersus.stock.entity.Stock;
import ru.nersus.stock.entity.User;
import ru.nersus.stock.enums.PeriodEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserDao userDao;
    StockDao stockDao;
    MoexApi moexApi;
    StockService stockService;

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
                        addStockDto.shortname(),
                        addStockDto.count(),
                        myUserDetails.getId()
                ));
    }

    public PortfolioDto getPortfolio(String email, PeriodEnum period) {
        List<Stock> userStocks = stockDao.getStocksByEmail(email);

        List<StockDto> stockDtos = new ArrayList<>();

        double portfolioCost = 0;
        for (Stock stock : userStocks) {
            SecurityDescription stockInfo = moexApi.getStockInfoBySymbol(stock.symbol());
            StockPrice stockPrice = moexApi.getStockPriceBySymbol(stock.symbol());

            IndicatorPredict predict;
            if (period != null) {
                List<Candle> stockPrices = stockService.getPricesBySymbolAndPeriod(stock.symbol(), period);
                predict = TechIndicatorsResults.getIndicators(stockPrices).generalPredict().predict();
            } else {
                predict = null;
            }


            stockDtos.add(new StockDto(stock.id(), stock.symbol(), stock.shortName(), stockPrice.last(), stock.count(), stockInfo.faceUnit(), predict, stockInfo));
            portfolioCost += stockPrice.last() * stock.count();
        }

        return new PortfolioDto(portfolioCost, stockDtos);
    }

    public void deleteStockById(int id, MyUserDetails myUserDetails) {
        stockDao.deleteStockById(id, myUserDetails);
    }
}
