package com.codev.domain.repository;

import com.codev.domain.dto.form.UserFiltersDTOForm;
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

    boolean followUser(UUID followedId, UUID followerId);

    boolean unfollowUser(UUID followedId, UUID followerId);

}
