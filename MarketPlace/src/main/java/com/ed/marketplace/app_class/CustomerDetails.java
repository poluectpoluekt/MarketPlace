package com.ed.marketplace.app_class;

import com.ed.marketplace.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails implements UserDetails {

    private Customer customer;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of();
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return customer.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return customer.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return customer.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return customer.isEnabled();
    }
}
