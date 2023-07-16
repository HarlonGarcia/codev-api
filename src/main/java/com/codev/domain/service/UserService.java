package com.codev.domain.service;

import com.codev.domain.dto.form.UserDTOForm;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.model.User;
import com.codev.domain.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public UserDTOView findUserById(Long id) {
        User user = User.findById(id);

        if (user == null)
            throw new EntityNotFoundException("User not found");

        return new UserDTOView(user);
    }

    @Transactional
    public UserDTOView createUser(UserDTOForm userDTOForm) {
        User user = new User(userDTOForm);
        user.persist();

        return new UserDTOView(user);
    }
}
