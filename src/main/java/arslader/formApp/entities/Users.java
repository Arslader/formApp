package arslader.formApp.entities;

import arslader.formApp.Views.Views;
import arslader.formApp.helpers.CustomAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name= "USERSFORM")
@JsonView(Views.UI.class)
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    @JsonView(Views.REST.class)
    private String password;

    @JsonView(Views.REST.class)
    private boolean active;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<Forms> forms = new ArrayList<Forms>();


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name="user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    public Users() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @JsonView(Views.REST.class)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonView(Views.REST.class)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonView(Views.REST.class)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonView(Views.REST.class)
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    @JsonView(Views.REST.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
