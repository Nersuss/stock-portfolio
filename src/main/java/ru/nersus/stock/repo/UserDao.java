package ru.nersus.stock.repo;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.nersus.stock.dto.RegisterRqDto;
import ru.nersus.stock.entity.User;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserDao {

    NamedParameterJdbcTemplate jdbcTemplate;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Boolean existByEmail(String email) {
        String sql = """
                SELECT EXISTS (SELECT 1 FROM public.users u WHERE u.email = :email);
                """;
        return jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource()
                        .addValue("email", email),
                Boolean.class
        );
    }

    public Optional<User> findByEmail(String email) {
        String sql = """
                SELECT id, email, password FROM public.users u WHERE u.email = :email;
                """;

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource()
                        .addValue("email", email),
                new DataClassRowMapper<>(User.class)
        ));
    }

    public void registerUser(RegisterRqDto registerRqDto) {
        String sql = """
                INSERT INTO public.users (email, password, balance, card_number) VALUES (:email, :password, :balance, :cardNumber);
                """;
        jdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("email", registerRqDto.login())
                        .addValue("password", bCryptPasswordEncoder().encode(registerRqDto.password()))
                        .addValue("balance", 100)
                        .addValue("cardNumber", UUID.randomUUID())
        );
    }

}
