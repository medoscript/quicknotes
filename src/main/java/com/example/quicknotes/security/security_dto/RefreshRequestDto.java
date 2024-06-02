package com.example.quicknotes.security.security_dto;

import java.util.Objects;

// При помощи данного объекта клиент будет отправлять
// на сервер рефреш токен с целью получения нового токена доступа
public class RefreshRequestDto {

    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshRequestDto that = (RefreshRequestDto) o;
        return Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken);
    }

    @Override
    public String toString() {
        return "RefreshRequestDto{" +
                "refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
