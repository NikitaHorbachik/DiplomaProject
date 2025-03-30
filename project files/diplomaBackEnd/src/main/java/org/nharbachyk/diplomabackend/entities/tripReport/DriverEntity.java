package org.nharbachyk.diplomabackend.entities.tripReport;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.nharbachyk.diplomabackend.entities.BaseEntity;
import org.nharbachyk.diplomabackend.entities.user.UserEntity;

@Entity
@Table(name = "drivers", schema = "diploma")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverEntity extends BaseEntity<Long> {

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @NotNull
    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Column(name = "phone")
    private String phone;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + getId() + '\'' +
                "licenseNumber='" + licenseNumber + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}