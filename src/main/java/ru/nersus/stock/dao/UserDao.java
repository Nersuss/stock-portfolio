package ru.nersus.stock.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.intellij.lang.annotations.Language;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.nersus.stock.dto.RegisterRqDto;
import ru.nersus.stock.entity.User;

import java.util.List;
import java.util.Optional;

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
        @Language("SQL")
        String sql = """
                SELECT EXISTS (SELECT 1 FROM public.users u WHERE u.email = :email);
                """;
        return jdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource()
                        .addValue("email", email),
                Boolean.class
        );
    }

    public Optional<User> getByEmail(String email) {
        @Language("SQL")
        String sql = """
                SELECT id, email, password FROM public.users u WHERE u.email = :email;
                """;

        List<User> users = jdbcTemplate.query(sql,
                new MapSqlParameterSource()
                        .addValue("email", email),
                new DataClassRowMapper<>(User.class)
        );
        return users.stream().findFirst();
    }

    public int registerUser(RegisterRqDto registerRqDto, String refreshToken) {
        @Language("SQL")
        String sql = """
                INSERT INTO public.users (email, password, token) VALUES (:email, :password, :refreshToken);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("email", registerRqDto.login())
                        .addValue("password", bCryptPasswordEncoder().encode(registerRqDto.password()))
                        .addValue("refreshToken", refreshToken),
                keyHolder,
                new String[]{"id"}
        );
        return keyHolder.getKey().intValue();
    }

    public void updateRefreshToken(String login, String refreshToken) {
        @Language("SQL")
        String sql = """
                UPDATE public.users SET token = :refreshToken WHERE email = :login;
                """;
        jdbcTemplate.update(sql,
                new MapSqlParameterSource()
                        .addValue("login", login)
                        .addValue("refreshToken", refreshToken)
        );
    }

}
