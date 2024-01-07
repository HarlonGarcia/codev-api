package com.codev.domain.repository;

import com.codev.domain.model.Role;

import java.util.UUID;

public interface RoleRepository {

    void addAdminRoleInUser(UUID userId);

    void addUserRoleInUser(UUID userId);

    void createRole(Role role);

}
