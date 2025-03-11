package org.nharbachyk.diplomabackend.mapper;

import org.nharbachyk.diplomabackend.controller.request.RoleRequest;
import org.nharbachyk.diplomabackend.entities.RoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleMapper {

    public RoleEntity toRoleEntity(RoleRequest roleRequest) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setAuthority(roleRequest.name());
        return roleEntity;
    }

    public GrantedAuthority toGrantedAuthority(RoleEntity roleEntity) {
        return new SimpleGrantedAuthority(roleEntity.getAuthority());
    }

    public List<GrantedAuthority> toGrantedAuthorityList(List<RoleEntity> entityList) {
        return entityList
                .stream()
                .map(this::toGrantedAuthority)
                .toList();
    }

}
