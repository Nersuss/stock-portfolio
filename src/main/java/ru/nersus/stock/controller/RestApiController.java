package ru.nersus.stock.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nersus.stock.dto.api.StockSearchDto;
import ru.nersus.stock.service.StockService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestApiController {
    StockService stockService;

    @GetMapping("/api/stocks")
    List<StockSearchDto> getSymbolsByChars(@RequestParam String query) throws IOException {
        List<StockSearchDto> symbols = stockService.getSymbolsByChars(query);

        if (symbols != null) {
            return symbols;
        }
        throw new NullPointerException();
    }

}
