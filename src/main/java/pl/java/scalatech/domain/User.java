package pl.java.scalatech.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Builder;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Lists;
import com.mysema.query.annotations.QueryEntity;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
@AllArgsConstructor
@Builder
@QueryEntity
@Slf4j
public class User extends PKEntity<String> implements UserDetails {

    private static final long serialVersionUID = -6567709458397827407L;
    private String fistName;
    private String lastName;
    private String thumbnailUrl;
    private String password;
    @Indexed(unique = true)
    private String login;
    @Indexed(unique = true)
    private String email;
    private String activatedCode;
    private boolean activated;
    
    private boolean logged;

    private Date lastLoginAt;

    private Date lastIncorrectLogin;

    private Date attemptLoginDate;

    private int attemptLoginCount;

    @DBRef
    private List<Role> roles = Lists.newArrayList();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthoritiesSet = new HashSet<>(getRoles().size());
        for (Role role : getRoles()) {
            grantedAuthoritiesSet.add(new SimpleGrantedAuthority("ROLE_"+role.getId().toUpperCase()));
        }
        log.info("roles  ++++++++++++++++++++++++++++++++++++++++++++++++   "+grantedAuthoritiesSet);
        return grantedAuthoritiesSet;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (!this.disabled && this.activated) { return true; }
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.disabled;
    }

}
