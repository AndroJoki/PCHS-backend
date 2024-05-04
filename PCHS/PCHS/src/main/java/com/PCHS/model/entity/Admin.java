package com.PCHS.model.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

/**
 *
 * @author andre
 */
@Data
@Entity
@Table(name = "admins", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "username"}))
public class Admin implements UserDetails{

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
    private String username;
	private String position;
	private String advisory;
    private String email;
    private String password;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "admin_auth_tokens",
            joinColumns = @JoinColumn(name = "id"),
            foreignKey = @ForeignKey(name = "admin_auth_token_fk")
    )
    private List<AdminAuthToken> adminAuthTokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    

}
