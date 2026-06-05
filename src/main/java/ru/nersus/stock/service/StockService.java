package ru.nersus.stock.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.nersus.stock.calculation.TechIndicatorsResults;
import ru.nersus.stock.dao.MoexApi;
import ru.nersus.stock.dto.LandingDto;
import ru.nersus.stock.dto.TechIndicatorsDto;
import ru.nersus.stock.dto.api.*;
import ru.nersus.stock.enums.PeriodEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StockService {

    MoexApi moexApi;

    public LandingDto getLanding(String symbol, PeriodEnum period) {
        SecurityDescription stockInfo = getStockInfoBySymbol(symbol);
        StockPrice stockPrice = getStockPriceBySymbol(symbol);
        List<Candle> stockPrices = getPricesBySymbolAndPeriod(symbol, period);
        TechIndicatorsDto indicators = TechIndicatorsResults.getIndicators(stockPrices);

        return new LandingDto(stockInfo, stockPrice, stockPrices, indicators);
    }

    String getLocalDateTimeFromPeriod(PeriodEnum period) {
        return switch (period) {
            case PeriodEnum.hour -> LocalDateTime.now().minusHours(1).toString();
            case PeriodEnum.day -> LocalDateTime.now().minusDays(1).toString();
            case PeriodEnum.week -> LocalDateTime.now().minusWeeks(1).toString();
            case PeriodEnum.month -> LocalDateTime.now().minusMonths(1).toString();
        };
    }

    public List<Candle> getPricesBySymbolAndPeriod(String symbol, PeriodEnum period) {
        return moexApi.getPricesBySymbolAndPeriod(symbol, getLocalDateTimeFromPeriod(period), period.getValue());
    }

    public List<StockSearchDto> getSymbolsByChars(String chars) {
        MoexSecuritiesRp symbolsByChars = moexApi.getSymbolsByChars(chars);
        List<SecurityInfo> securityInfos = symbolsByChars.securities().data().stream().map(SecurityInfo::fromList).toList();
        return securityInfos.stream().map(stock -> new StockSearchDto(
                stock.shortname(),
                stock.shortname(),
                "/?symbol=" + stock.secid() + "&from=2026-01-01"
        )).collect(Collectors.toList());
    }

    public SecurityDescription getStockInfoBySymbol(String symbol) {
        return moexApi.getStockInfoBySymbol(symbol);
    }
    public StockPrice getStockPriceBySymbol(String symbol) {
        return moexApi.getStockPriceBySymbol(symbol);
    }

}
