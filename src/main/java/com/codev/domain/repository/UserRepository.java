package com.codev.domain.repository;

import com.codev.domain.dto.form.UserFiltersDTOForm;
import com.codev.domain.dto.view.DashboardMetricsDtoView;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    List<User> findAllUsers(UserFiltersDTOForm filters, Integer page, Integer size);

    List<UserDTOView> findAllFollowedUsers(UUID followerId, Integer page, Integer size);

    User findByEmail(String email);

    User findById(UUID userId);

    void createUser(User user);

    void followUser(UUID followedId, UUID followerId);

    void unfollowUser(UUID followedId, UUID followerId);

    List<User> findAllUsersForChallenge(UUID challengeId, Integer page, Integer size);

    DashboardMetricsDtoView generateChallengesDashboardMetrics(UUID userId);
}
