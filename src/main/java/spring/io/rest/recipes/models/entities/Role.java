package spring.io.rest.recipes.models.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    Long id;
    private String authority;

    public Role(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Role() {
    }

    public static RoleBuilder builder() {
        return new RoleBuilder();
    }

    @Override
    public String getAuthority() {
        return authority;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return getId().equals(role.getId()) && getAuthority().equals(role.getAuthority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthority());
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class RoleBuilder {
        private Long id;
        private String authority;

        RoleBuilder() {
        }

        public RoleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RoleBuilder authority(String authority) {
            this.authority = authority;
            return this;
        }

        public Role build() {
            return new Role(id, authority);
        }

        public String toString() {
            return "Role.RoleBuilder(id=" + this.id + ", authority=" + this.authority + ")";
        }
    }
}