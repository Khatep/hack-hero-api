package com.hackhero.domainmodule.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hackhero.domainmodule.entities.AbstractEntity;
import com.hackhero.domainmodule.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth_users")
@Entity
public class AuthUser extends AbstractEntity implements Comparable<AuthUser>, UserDetails {
    /**
     * Used like unique username in Security
     * */
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int compareTo(AuthUser o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        AuthUser authUser = (AuthUser) o;
        return getId() != null && Objects.equals(getId(), authUser.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
