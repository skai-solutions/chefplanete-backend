package net.skai.chefplaneteapi.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class User {

    private Integer userId;

    private String givenName;

    private String email;

    private List<Role> roles;

    public User(@NotNull final Integer userId,
                @NotNull final String email,
                @NotNull final String givenName,
                @NotNull final List<Role> roles) {
        this.userId = userId;
        this.email = email;
        this.givenName = givenName;
        this.roles = roles;
    }

    @NotNull
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull final Integer userId) {
        this.userId = userId;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull final String email) {
        this.email = email;
    }

    @NotNull
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(@NotNull final List<Role> roles) {
        this.roles = roles;
    }

    @NotNull
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(@NotNull final String givenName) {
        this.givenName = givenName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId) &&
                givenName.equals(user.givenName) &&
                email.equals(user.email) &&
                roles.equals(user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, givenName, email, roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", givenName='" + givenName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
