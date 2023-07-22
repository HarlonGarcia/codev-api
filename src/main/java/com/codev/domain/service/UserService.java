package com.codev.domain.service;

import com.codev.domain.dto.form.UserDTOForm;
import com.codev.domain.dto.form.UserFiltersDTOForm;
import com.codev.domain.dto.view.UserDTOView;
import com.codev.domain.model.User;
import com.codev.domain.repository.UserRepository;
import com.codev.utils.helpers.NullAwareBeanUtilsBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> findAllUsers(UserFiltersDTOForm filters) {
        return userRepository.findAllUsers(filters);
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

    @Transactional
    public UserDTOView updateUser(Long id, UserDTOForm userDTOForm) throws InvocationTargetException, IllegalAccessException {
        User user = User.findById(id);

        if (user == null)
            throw new EntityNotFoundException("User not found");

        NullAwareBeanUtilsBean.getInstance().copyProperties(user, userDTOForm);
        user.setUpdatedAt(LocalDateTime.now());
        user.persist();

        return new UserDTOView(user);
    }
}
