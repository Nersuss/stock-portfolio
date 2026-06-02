package ru.nersus.stock.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.nersus.stock.entity.User;

import java.util.Collection;

@Getter
public class MyUserDetails implements UserDetails {

    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.password();
    }

    public Integer getId() {
        return user.id();
    }

    @Override
    public String getUsername() {
        return user.email();
    }

}
