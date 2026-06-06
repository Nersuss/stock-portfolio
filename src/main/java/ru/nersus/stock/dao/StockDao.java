package ru.nersus.stock.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.intellij.lang.annotations.Language;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nersus.stock.config.MyUserDetails;
import ru.nersus.stock.entity.Stock;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StockDao {

    NamedParameterJdbcTemplate jdbcTemplate;

    public List<Stock> getStocksByEmail(String email) {
        @Language("SQL")
        String sql = """
                SELECT s.id, symbol, shortname, count, owner_id FROM public.stock s 
                JOIN public.users u ON u.id=s.owner_id WHERE u.email = :email;
                """;

        return jdbcTemplate.query(sql,
                new MapSqlParameterSource()
                        .addValue("email", email),
                new DataClassRowMapper<>(Stock.class)
        );
    }

    public void addByUserId(Stock stock) {
        @Language("SQL")
        String sql = """
                INSERT INTO public.stock (symbol, shortname, count, owner_id) VALUES (:symbol, :shortname, :count, :owner_id);
                """;
        jdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("symbol", stock.symbol())
                        .addValue("shortname", stock.shortName())
                        .addValue("count", stock.count())
                        .addValue("owner_id", stock.ownerId())
        );
    }

    public void deleteStockById(int id, MyUserDetails myUserDetails) {
        @Language("SQL")
        String sql = """
                DELETE FROM public.stock s WHERE s.id = :id;
                """;
        jdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("id", id)
        );
    }
}
