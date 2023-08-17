package com.codev.domain.service;

import com.codev.api.security.auth.AuthRequest;
import com.codev.api.security.auth.AuthResponse;
import com.codev.api.security.auth.PBKDF2Encoder;
import com.codev.api.security.token.TokenUtils;
import com.codev.domain.dto.form.UserDTOForm;
import com.codev.domain.dto.form.UserFiltersDTOForm;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.exceptions.token.GenerateTokenExcepetion;
import com.codev.domain.exceptions.users.UnathorizedLoginMessage;
import com.codev.domain.exceptions.users.UserDeactivatedException;
import com.codev.domain.model.Role;
import com.codev.domain.model.User;
import com.codev.domain.repository.UserRepository;
import com.codev.utils.GlobalConstants;
import com.codev.utils.helpers.DtoTransformer;
import com.codev.utils.helpers.NullAwareBeanUtilsBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    PBKDF2Encoder passwordEncoder;

    public List<UserDTOView> findAllUsers(UserFiltersDTOForm filters) {
        List<User> users = userRepository.findAllUsers(filters);

        DtoTransformer<User, UserDTOView> transformer = new DtoTransformer<>();
        List<UserDTOView> userDTOList = transformer.transformToDTOList(users, UserDTOView.class);
        
        return userDTOList;
    }

    public UserDTOView findUserById(UUID id) throws UserDeactivatedException {
        User user = User.findById(id);

        if (user == null)
            throw new EntityNotFoundException("User not found");
        if (!user.isActive())
            throw new UserDeactivatedException();

        return new UserDTOView(user);
    }

    @Transactional
    public UserDTOView createUser(UserDTOForm userDTOForm) {

        User user = new User(userDTOForm);
        user.setPassword(passwordEncoder.encode(userDTOForm.getPassword()));

        Role role = new Role("USER");
        role.setId(GlobalConstants.USER_ROLE_ID);

        user.getRoles().add(role);
        user.persist();

        return new UserDTOView(user);
    }

    @Transactional
    public UserDTOView updateUser(UUID id, UserDTOForm userDTOForm) throws InvocationTargetException, IllegalAccessException {
        User user = User.findById(id);

        if (user == null)
            throw new EntityNotFoundException("User not found");

        NullAwareBeanUtilsBean.getInstance().copyProperties(user, userDTOForm);
        user.setUpdatedAt(LocalDateTime.now());
        user.persist();

        return new UserDTOView(user);
    }

    @Transactional
    public void deactivateUser(UUID id) throws UserDeactivatedException {
        User user = User.findById(id);

        if (user == null)
            throw new EntityNotFoundException("User not found");
        if (!user.isActive())
            throw new UserDeactivatedException();

        user.setActive(GlobalConstants.DEACTIVATE);
        user.persist();
    }

    @Transactional
    public AuthResponse login(AuthRequest authRequest) throws GenerateTokenExcepetion, UnathorizedLoginMessage {
        User user = userRepository.findByUsername(authRequest.username);
        String passwordEncode = passwordEncoder.encode(authRequest.password);

        if (user != null && user.getPassword().equals(passwordEncode)) {
            try {
                return new AuthResponse(TokenUtils.generateToken(user.getEmail(), user.getRoles()));
            } catch (Exception e) {
                throw new GenerateTokenExcepetion();
            }
        } else {
            throw new UnathorizedLoginMessage();
        }
    }
}
