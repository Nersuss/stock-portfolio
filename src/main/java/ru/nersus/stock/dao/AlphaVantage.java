package ru.nersus.stock.dao;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nersus.stock.dto.api.BestMatchesDto;
import ru.nersus.stock.dto.api.GlobalQuoteDto;
import ru.nersus.stock.dto.api.GlobalQuoteRpDto;
import ru.nersus.stock.dto.api.StockPricesDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AlphaVantage {

    @Autowired
    Gson gson;

    @Autowired
    RestTemplate restTemplate;

    String API_KEY = "ZEBSZMBM169YX5Z7"; //  FBHPMXR7LC2AVHK0   ZEBSZMBM169YX5Z7 ETYJF8AVBD4G6WO2

    @Value("classpath:response/get_IBM_DAILY.json")
    Resource AAPL_WEEKLY_STUB;
    public StockPricesDto getPricesBySymbolAndPeriod(@NonNull String symbol, @NonNull String period) throws IOException {
//        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_%s&symbol=%s&apikey=%s&datatype=json".formatted(period, symbol, API_KEY);
//        String json = restTemplate.getForObject(url, String.class);
//        return gson.fromJson(json, StockPricesDto.class);
        String json = Files.readString(Path.of(AAPL_WEEKLY_STUB.getFile().getPath()));
        return gson.fromJson(json, StockPricesDto.class);
    }

    @Value("classpath:response/symbol_search_BA.json")
    Resource symbol_search_BA_STUB;
    public BestMatchesDto getSymbolsByChars(@NonNull String chars) throws IOException {
        String url = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=%s&apikey=%s&datatype=json".formatted(chars, API_KEY);
        String json = restTemplate.getForObject(url, String.class);
        //String json = Files.readString(Path.of(symbol_search_BA_STUB.getFile().getPath()));
        return gson.fromJson(json, BestMatchesDto.class);

//        return gson.fromJson(stubRp2, BestMatchesDto.class);
    }

    @Value("classpath:response/IBM_INFO.json")
    Resource IBM_INFO_STUB;
    public GlobalQuoteDto getStockInfoBySymbol(@NonNull String symbol) throws IOException {
//        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s&datatype=json".formatted(symbol, API_KEY);
//        String json = restTemplate.getForObject(url, String.class);
        String json = Files.readString(Path.of(IBM_INFO_STUB.getFile().getPath()));
        return gson.fromJson(json, GlobalQuoteRpDto.class).globalQuote();
    }

}
