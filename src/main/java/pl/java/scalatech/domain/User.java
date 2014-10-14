package pl.java.scalatech.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends CommonNeo4jEntity implements Comparable<User>,UserDetails  {

    @Indexed(unique = true)
    private @NonNull @NotNull @Email String email;

    private @NonNull @NotNull @Size(min = 3, max = 30) String login;

    private @NonNull @NotNull @Size(min = 6, max = 30) String password;

    @RelatedTo(type = RelTypes.ROLES)
    @Fetch
    private Set<Role> roles = new TreeSet<>();

    private String fistName;
    private String lastName;
    private String thumbnailUrl;

    private String activatedCode;
    private boolean activated;

    private boolean logged;

    private LocalDate lastLoginAt;

    private LocalDate lastIncorrectLogin;

    private LocalDate attemptLoginDate;

    private int attemptLoginCount;

    @Override
    public int compareTo(User o) {
        return this.login.compareTo(o.login);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }
}
