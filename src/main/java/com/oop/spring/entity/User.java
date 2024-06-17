package com.oop.spring.entity;

import lombok.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@Getter
@Component
public class User {
    private Long id;
    private String login;
    private List<Account> accountList;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", login='" + login + '\'' +
               ", accountList=" + accountList +
               '}';
    }
}
