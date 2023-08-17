package com.codev.domain.repository;

import java.util.UUID;

public interface RoleRepository {

    void addAdminRoleInUser(UUID userId);

}
