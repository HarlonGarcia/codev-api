package com.codev.domain.service;

import com.codev.api.security.auth.AuthRequest;
import com.codev.api.security.auth.AuthResponse;
import com.codev.api.security.auth.PBKDF2Encoder;
import com.codev.api.security.token.TokenUtils;
import com.codev.domain.dto.form.UserDTOForm;
import com.codev.domain.dto.form.UserFiltersDTOForm;
import com.codev.domain.dto.form.UserUpdateDTOForm;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.exceptions.global.UniqueConstraintViolationException;
import com.codev.domain.exceptions.token.GenerateTokenException;
import com.codev.domain.exceptions.users.InvalidLoginException;
import com.codev.domain.exceptions.users.UserDeactivatedException;
import com.codev.domain.exceptions.users.UserHasAdminRoleException;
import com.codev.domain.model.Role;
import com.codev.domain.model.User;
import com.codev.domain.repository.RoleRepository;
import com.codev.domain.repository.UserRepository;
import com.codev.utils.GlobalConstants;
import com.codev.utils.helpers.DtoTransformer;
import com.codev.utils.helpers.NullAwareBeanUtilsBean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class UserService {

    @PersistenceContext
    EntityManager entityManager;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PBKDF2Encoder passwordEncoder;

    public List<UserDTOView> findAllUsers(UserFiltersDTOForm filters, Integer page, Integer size) {
        List<User> users = userRepository.findAllUsers(filters, page, size);

        DtoTransformer<User, UserDTOView> transformer = new DtoTransformer<>();
        List<UserDTOView> userDTOList = transformer.transformToDTOList(users, UserDTOView.class);

        return userDTOList;
    }

    public List<UserDTOView> findAllFollowedUsers(UUID followerId, Integer page, Integer size) {
        return userRepository.findAllFollowedUsers(followerId, page, size);
    }

    public User findUserById(UUID userId) throws UserDeactivatedException {
        User user = userRepository.findById(userId);

        if (!user.isActive())
            throw new UserDeactivatedException();

        return user;
    }

    public User findByEmail(String email) throws UserDeactivatedException {
        User user = userRepository.findByEmail(email);

        if (!user.isActive())
            throw new UserDeactivatedException();

        return user;
    }

    @Transactional
    public void followUser(UUID followedId, UUID followerId) {
        userRepository.followUser(followedId, followerId);
    }

    @Transactional
    public AuthResponse createUser(UserDTOForm userDTOForm) throws UniqueConstraintViolationException, GenerateTokenException {
        initializeRoles();

        User user = new User(userDTOForm);
        user.setPassword(passwordEncoder.encode(userDTOForm.getPassword()));

        Role userRole = new Role("USER");
        userRole.setId(GlobalConstants.USER_ROLE_ID);

        user.getRoles().add(userRole);

        userRepository.createUser(user);
        roleRepository.addUserRoleInUser(user.getId());

        return new AuthResponse(TokenUtils.generateToken(user.getEmail(), user.getRoles()));
    }

    public void initializeRoles() {
        if (Role.count() == 0) {
            Role userRole = new Role("USER");
            userRole.setId(GlobalConstants.USER_ROLE_ID);

            Role adminRole = new Role("ADMIN");
            adminRole.setId(GlobalConstants.ADMIN_ROLE_ID);

            roleRepository.createRole(userRole);
            roleRepository.createRole(adminRole);
        }
    }

    @Transactional
    public UserDTOView addAdminRoleInUser(UUID userId) throws UserDeactivatedException, UserHasAdminRoleException {
        User user = findUserById(userId);

        for (Role role : user.getRoles()) {
            if (role.getName().equals("ADMIN")) {
                throw new UserHasAdminRoleException();
            }
        }

        Role role = new Role("ADMIN");
        role.setId(GlobalConstants.ADMIN_ROLE_ID);

        user.getRoles().add(role);

        roleRepository.addAdminRoleInUser(userId);
        return new UserDTOView(user);
    }

    @Transactional
    public UserDTOView updateUser(
        UUID userId,
        UserUpdateDTOForm userDTOForm
    ) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        User user = User.findById(userId);

        if (user == null)
            throw new EntityNotFoundException(
                String.format("User with id %s does not exist", userId)
            );

        if (userDTOForm.getImage() == null) {
            user.setImage(null);
            entityManager.merge(user);
        }

        NullAwareBeanUtilsBean copyUtils = new NullAwareBeanUtilsBean();
        copyUtils.copyProperties(user, userDTOForm);

        user.setUpdatedAt(LocalDateTime.now());
        user.persist();

        return new UserDTOView(user);
    }

    @Transactional
    public void deactivateUser(UUID userId) throws UserDeactivatedException {
        User user = User.findById(userId);

        if (user == null)
            throw new EntityNotFoundException(String.format("User with id %s does not exist", userId));
        if (!user.isActive())
            throw new UserDeactivatedException();

        user.setActive(GlobalConstants.DEACTIVATE);
        user.persist();
    }

    @Transactional
    public AuthResponse login(AuthRequest authRequest) throws InvalidLoginException {
        User user = userRepository.findByEmail(authRequest.email);
        String passwordEncode = passwordEncoder.encode(authRequest.password);

        if (user != null && passwordEncode.equals(user.getPassword())) {
            return new AuthResponse(TokenUtils.generateToken(user.getEmail(), user.getRoles()));
        } else {
            throw new InvalidLoginException();
        }
    }

    @Transactional
    public void unfollowUser(UUID followedId, UUID followerId) {
        userRepository.unfollowUser(followedId, followerId);
    }
}