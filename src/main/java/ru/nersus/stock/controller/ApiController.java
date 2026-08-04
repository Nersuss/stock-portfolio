package ru.nersus.stock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nersus.stock.dto.api.StockSearchDto;
import ru.nersus.stock.service.StockService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Rest-контроллер для отправки запросов к внешнему API", description = "")
@Slf4j
public class ApiController {
    StockService stockService;

    @GetMapping("/api/stocks")
    @Operation(
            summary = "Поиск акций по названию компаний и тикеру",
            description = "Возвращает список акций соответствующих запросу"
    )
    List<StockSearchDto> getSymbolsByChars(@RequestParam String query) {
        List<StockSearchDto> symbols = stockService.getSymbolsByChars(query);

        if (symbols != null) {
            return symbols;
        }
        log.warn("Error in search companies by substring. Query: {}", query);
        throw new IllegalArgumentException();
    }

}
