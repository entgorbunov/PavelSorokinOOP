package com.oop.spring.entity;

import lombok.*;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.Objects;

@Builder
@AllArgsConstructor
@Setter
@Getter
@Component
public class Account {
    private final Long id;
    private final Long userId;
    private BigDecimal moneyAmount;


    @Override
    public String toString() {
        return "Account{" +
               "id=" + id +
               ", userId=" + userId +
               ", moneyAmount=" + moneyAmount +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
