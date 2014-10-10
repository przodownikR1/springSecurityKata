package pl.java.scalatech.assembler;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.hateoas.ResourceSupport;

import pl.java.scalatech.domain.Role;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class UserResource extends ResourceSupport {

    private String userId;

    private String login;

    private String email;

    private boolean activated;

    private boolean disabled;

    private String thumbnailUrl;

    private List<Role> roles;

    private String firstName;

    private String lastName;

    private boolean logged;

    private Date lastLoginAt;

    private Date lastIncorrectLogin;

    private Date attemptLoginDate;

    private int attemptLoginCount;
    
}
