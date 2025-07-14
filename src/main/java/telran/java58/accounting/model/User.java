package telran.java58.accounting.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(of = "login")
public class User {
    @Id
    private String login;
    @Setter
    private String password;
    @Setter
    private String firstName;
    @Setter
    private String lastName;
    private Set<String> roles;

    public User (){
        roles = new HashSet<>();
        roles.add("USER");
    }
    public User(String login, String firstName, String password, String lastName) {
        this();
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addRole(String role) {
        roles.add(role);
    }

    public void deleteRole(String role) {
        roles.remove(role);
    }
}
