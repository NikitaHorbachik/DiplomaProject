package org.nharbachyk.diplomabackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "u_roles")
@Getter
@Setter
public class RoleEntity extends BaseEntity<Long> implements GrantedAuthority {

    private String authority;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [authority=" + authority + "]";
    }
}
