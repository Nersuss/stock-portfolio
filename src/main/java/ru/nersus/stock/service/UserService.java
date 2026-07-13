package ru.nersus.stock.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.nersus.stock.calculation.IndicatorPredict;
import ru.nersus.stock.calculation.TechIndicatorsResults;
import ru.nersus.stock.dao.MoexApi;
import ru.nersus.stock.dao.StockDao;
import ru.nersus.stock.dto.AddStockDto;
import ru.nersus.stock.dto.PortfolioDto;
import ru.nersus.stock.dto.StockDto;
import ru.nersus.stock.dto.api.Candle;
import ru.nersus.stock.dto.api.SecurityDescription;
import ru.nersus.stock.dto.api.StockPrice;
import ru.nersus.stock.entity.Stock;
import ru.nersus.stock.enums.PeriodEnum;
import ru.nersus.stock.jwt.JwtAuthentication;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    StockDao stockDao;
    MoexApi moexApi;
    StockService stockService;

    public void addStockByEmail(AddStockDto addStockDto, JwtAuthentication myUserDetails) {
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

        double currentPortfolioCost = 0;
        double previousPortfolioCost = 0;

        for (Stock stock : userStocks) {
            SecurityDescription stockInfo = moexApi.getStockInfoBySymbol(stock.symbol());
            StockPrice stockPrice = moexApi.getStockPriceBySymbol(stock.symbol());

            IndicatorPredict predict = null;
            Double oldPrice;

            if (period != null) {
                List<Candle> stockPrices = stockService.getPricesBySymbolAndPeriod(stock.symbol(), period);
                predict = TechIndicatorsResults.getIndicators(stockPrices).generalPredict().predict();

                // Берем цену открытия первой свечи как старую цену
                if (!stockPrices.isEmpty()) {
                    oldPrice = stockPrices.get(0).open();
                    previousPortfolioCost += oldPrice * stock.count();
                }
            }

            double currentStockCost = stockPrice.last() * stock.count();
            currentPortfolioCost += currentStockCost;

            stockDtos.add(new StockDto(stock.id(), stock.symbol(), stock.shortName(), stockPrice.last(), stock.count(), stockInfo.faceUnit(), predict, stockInfo));

        }

        // Расчет изменения портфеля
        double portfolioChange = currentPortfolioCost - previousPortfolioCost;
        double portfolioChangePercent = previousPortfolioCost > 0
                ? (portfolioChange / previousPortfolioCost) * 100
                : 0;

        return new PortfolioDto(currentPortfolioCost, stockDtos, portfolioChange, portfolioChangePercent);
    }

    public void deleteStockById(int id, JwtAuthentication myUserDetails) {
        stockDao.deleteStockById(id, myUserDetails);
    }
}
