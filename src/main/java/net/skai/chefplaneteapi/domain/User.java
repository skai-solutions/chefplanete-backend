package net.skai.chefplaneteapi.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;

import java.util.Objects;

public class User {

    @Id
    private final String userId;

    private final String givenName;

    private final String email;

    public User(@NotNull final String userId,
                @NotNull final String email,
                @NotNull final String givenName) {
        this.userId = userId;
        this.email = email;
        this.givenName = givenName;
    }

    @NotNull
    public String getUserId() {
        return userId;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    @NotNull
    public String getGivenName() {
        return givenName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId) &&
                givenName.equals(user.givenName) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, givenName, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", givenName='" + givenName + '\'' +
                ", email='" + email +
                '}';
    }
}
