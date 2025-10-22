package ru.nersus.stock.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.nersus.stock.calculation.TechIndicatorsResults;
import ru.nersus.stock.dto.LandingDto;
import ru.nersus.stock.dto.TechIndicatorsDto;
import ru.nersus.stock.dto.api.BestMatchesDto;
import ru.nersus.stock.dto.api.GlobalQuoteDto;
import ru.nersus.stock.dto.api.StockPricesDto;
import ru.nersus.stock.dto.api.StockSearchDto;
import ru.nersus.stock.repo.AlphaVantage;
import ru.nersus.stock.repo.StockRepo;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StockService {

    StockRepo stockRepo;
    AlphaVantage alphaVantage;

    public LandingDto getLanding(String symbol) throws IOException {
        GlobalQuoteDto info = getStockInfoBySymbol(symbol);
        StockPricesDto prices = getPricesBySymbolAndPeriod(symbol, "DAILY");
        TechIndicatorsDto indicators = TechIndicatorsResults.getIndicators(prices.getFullPrices());
        return new LandingDto(info, prices.getLabels(), prices.getOpenPrices(), indicators);
    }

    public StockPricesDto getPricesBySymbolAndPeriod(String symbol, String period) throws IOException {
        return alphaVantage.getPricesBySymbolAndPeriod(symbol, period);
    }

    public List<StockSearchDto> getSymbolsByChars(String chars) throws IOException {
        BestMatchesDto symbolsByChars = alphaVantage.getSymbolsByChars(chars);
        return symbolsByChars.bestMatches().stream().map(stock -> new StockSearchDto(
                stock.symbol(),
                stock.name(),
                "/?symbol=" + stock.symbol() + "&period=WEEKLY"
        )).collect(Collectors.toList());
    }

    public GlobalQuoteDto getStockInfoBySymbol(String chars) throws IOException {
        return alphaVantage.getStockInfoBySymbol(chars);
    }

}
