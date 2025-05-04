package org.nharbachyk.diplomabackend.entities.organization;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nharbachyk.diplomabackend.entities.BaseEntity;
import org.nharbachyk.diplomabackend.entities.user.UserEntity;

import java.util.List;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
public class OrganizationEntity extends BaseEntity<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "organization")
    private List<UserEntity> users;
}
