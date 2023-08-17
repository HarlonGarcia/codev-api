package com.codev.infraestructure.impl;

import com.codev.domain.repository.RoleRepository;
import com.codev.utils.GlobalConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final DataSource dataSource;

    @Override
    public void addAdminRoleInUser(UUID userId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO tb_user_role (user_id, role_id) VALUES (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, userId);
                statement.setObject(2, GlobalConstants.ADMIN_ROLE_ID);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
