package org.nharbachyk.diplomabackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "u_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends BaseEntity<Long> {

    @Column(name = "name")
    private String authority;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [authority=" + authority + "]";
    }
}
