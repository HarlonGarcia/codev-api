package com.codev.infraestructure.impl;

import com.codev.domain.dto.view.LikeDTOView;
import com.codev.domain.dto.view.SolutionDTOView;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.exceptions.solutions.LikeNotAcceptedException;
import com.codev.domain.exceptions.solutions.SolutionNotDeletedException;
import com.codev.domain.model.User;
import com.codev.domain.repository.SolutionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SolutionRepositoryImpl implements SolutionRepository {

    private final EntityManager entityManager;

    public SolutionRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Inject
    DataSource dataSource;

    @Override
    public List<SolutionDTOView> findAllSolutionsByChallengeId(
            UUID challengeId, UUID userId, Integer page, Integer size
    ) {

        if (page < 0) {
            throw new IllegalArgumentException("Page must be a positive integer.");
        }

        List<SolutionDTOView> solutionDTOViews = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT " +
                    "s1_0.challenge_id, " +
                    "a1_0.id, " +
                    "a1_0.active, " +
                    "a1_0.additional_url, " +
                    "a1_0.created_at, " +
                    "a1_0.email, " +
                    "a1_0.github_url, " +
                    "a1_0.name, " +
                    "a1_0.password, " +
                    "a1_0.updated_at, " +
                    "s1_0.repository_url, " +
                    "s1_0.deploy_url, " +
                    "COALESCE(l1_0.likes_count, 0) as likes, " +
                    "l1_0.liked, " +
                    "s1_0.id " +
                    "FROM tb_solution s1_0 " +
                    "LEFT JOIN tb_user a1_0 ON a1_0.id = s1_0.author_id " +
                    "LEFT JOIN (" +
                    "    SELECT " +
                    "        p1_0.solution_id, " +
                    "        COUNT(DISTINCT p1_0.participant_id) as likes_count, " +
                    "        EXISTS (" +
                    "            SELECT 1 " +
                    "            FROM tb_like p2_0 " +
                    "            WHERE p2_0.solution_id = p1_0.solution_id AND p2_0.participant_id = ? " +
                    "        ) as liked " +
                    "    FROM tb_like p1_0 " +
                    "    GROUP BY p1_0.solution_id " +
                    ") l1_0 ON l1_0.solution_id = s1_0.id " +
                    "WHERE s1_0.challenge_id = ? " +
                    "ORDER BY s1_0.id " +
                    "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, userId);
                statement.setObject(2, challengeId);
                statement.setInt(3, page * size); // Calculate the offset
                statement.setInt(4, size);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        // Map the columns to SolutionDTOView properties
                        SolutionDTOView solutionDTOView = new SolutionDTOView();
                        User author = new User();

                        solutionDTOView.setChallengeId(UUID.fromString(resultSet.getString(1)));
                        author.setId(UUID.fromString(resultSet.getString(2)));
                        author.setActive(resultSet.getBoolean(3));
                        author.setAdditionalUrl(resultSet.getString(4));
//                        author.setCreatedAt(resultSet.getTimestamp(5).toLocalDateTime());
                        author.setEmail(resultSet.getString(6));
                        author.setGithubUrl(resultSet.getString(7));
                        author.setName(resultSet.getString(8));
                        author.setPassword(resultSet.getString(9));
//                        author.setUpdatedAt(resultSet.getTimestamp(10).toLocalDateTime());
                        // todo: arrumar as datas para retornar corretamente e nao da problema NullPointerException
                        solutionDTOView.setRepositoryUrl(resultSet.getString(11));
                        solutionDTOView.setDeployUrl(resultSet.getString(12));
                        solutionDTOView.setLikes(resultSet.getLong(13));
                        solutionDTOView.setLiked(resultSet.getBoolean(14));
                        solutionDTOView.setSolutionId(UUID.fromString(resultSet.getString(15)));

                        solutionDTOView.setAuthor(new UserDTOView(author));

                        solutionDTOViews.add(solutionDTOView);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return solutionDTOViews;

    }

    @Override
    public LikeDTOView addLike(UUID solutionId, UUID userId) throws LikeNotAcceptedException {
        boolean isLikedInSolution = isLikedInSolution(solutionId, userId);

        if (!isLikedInSolution)
            return addLikeInSolution(solutionId, userId);
        else
            throw new LikeNotAcceptedException();
    }

    @Override
    public LikeDTOView removeLike(UUID solutionId, UUID userId) throws LikeNotAcceptedException {
        boolean isLikedInSolution = isLikedInSolution(solutionId, userId);

        if (isLikedInSolution)
            return removeLikeBySolutionIdAndparticipantId(solutionId, userId);
        else
            throw new LikeNotAcceptedException();
    }

    private LikeDTOView addLikeInSolution(UUID solutionId, UUID userId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO tb_like (participant_id, solution_id) VALUES (?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, userId);
                statement.setObject(2, solutionId);
                statement.executeUpdate();

                return new LikeDTOView(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private LikeDTOView removeLikeBySolutionIdAndparticipantId(UUID solutionId, UUID participantId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM tb_like WHERE participant_id = ? AND solution_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, participantId);
                statement.setObject(2, solutionId);
                statement.executeUpdate();

                return new LikeDTOView(false);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isLikedInSolution(UUID solutionId, UUID participantId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT COUNT(*) AS count" +
                    " FROM tb_like" +
                    " WHERE participant_id = ? AND solution_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, participantId);
                statement.setObject(2, solutionId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        return count > 0;
                    }
                    throw new RuntimeException("result.next() == false. There are no more rows");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteSolution(UUID solutionId, UUID authorId) throws SolutionNotDeletedException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM tb_solution WHERE author_id = ? AND id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, authorId);
                statement.setObject(2, solutionId);
                statement.executeUpdate();

                return true;
            }

        } catch (SQLException e) {
            throw new SolutionNotDeletedException(e.getMessage());
        }
    }

    public void removeLikeBySolutionId(UUID solutionId) throws SolutionNotDeletedException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM tb_like WHERE solution_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setObject(1, solutionId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SolutionNotDeletedException(e.getMessage());
        }
    }

}
