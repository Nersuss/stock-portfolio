package ru.nersus.stock.jwt;

public record JwtRp(
        String accessToken,
        String refreshToken
) {
}
