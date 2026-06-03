package ru.nersus.stock.dao;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nersus.stock.dto.api.*;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MoexApi {

    Gson gson;
    RestTemplate restTemplate;

    public List<Candle> getPricesBySymbolAndPeriod(@NonNull String symbol, @NonNull LocalDate from) {
        String url = "https://iss.moex.com/iss/engines/stock/markets/shares/securities/%s/candles.json?from=%s&till=2026-06-01&interval=24".formatted(symbol, from.toString());
        String json = restTemplate.getForObject(url, String.class);
        MoexCandlesRp moexCandlesRp = gson.fromJson(json, MoexCandlesRp.class);
        List<Candle> candles = moexCandlesRp.candles().data().stream().map(Candle::fromList).toList();
        return candles;
    }

    public MoexSecuritiesRp getSymbolsByChars(@NonNull String chars) {
        String url = "https://iss.moex.com/iss/securities.json?q=%s".formatted(chars);
        String json = restTemplate.getForObject(url, String.class);
        return gson.fromJson(json, MoexSecuritiesRp.class);
    }

    public SecurityDescription getStockInfoBySymbol(@NonNull String symbol) {
        String url = "https://iss.moex.com/iss/securities/%s.json".formatted(symbol);
        String json = restTemplate.getForObject(url, String.class);
        MoexSecurityDescriptionRp moexSecurityDescriptionRp = gson.fromJson(json, MoexSecurityDescriptionRp.class);
        SecurityDescription securityDescription = SecurityDescription.fromDataList(moexSecurityDescriptionRp.description().data());
        return securityDescription;
    }


    public StockPrice getStockPriceBySymbol(@NonNull String symbol) {
        String url = "https://iss.moex.com/iss/engines/stock/markets/shares/securities/%s.json".formatted(symbol);
        String json = restTemplate.getForObject(url, String.class);
        MoexMarketDataRp moexMarketDataRp = gson.fromJson(json, MoexMarketDataRp.class);
        StockPrice stockPrice = moexMarketDataRp.marketdata().data().stream()
                .map(StockPrice::fromMarketDataList)
                .filter(price -> "TQBR".equals(price.boardid()))
                .findFirst()
                .orElse(null);
        return stockPrice;
    }

}
