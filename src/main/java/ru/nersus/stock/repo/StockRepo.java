package ru.nersus.stock.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nersus.stock.entity.Stock;

import java.util.List;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {

    List<Stock> getStockByOwner_Email(String email);

//    Stock saveByUserEmail(Stock stock, String email);
}
