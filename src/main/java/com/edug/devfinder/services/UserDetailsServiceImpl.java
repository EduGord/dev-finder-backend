package com.edug.devfinder.services;

import com.edug.devfinder.exceptions.LoginAttemptBlockedException;
import com.edug.devfinder.messages.MessagesEnum;
import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.entities.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
    private final EntityManagerFactory entityManagerFactory;
    private final LoginAttemptsService loginAttemptsService;
    private final HttpServletRequest request;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, LoginAttemptBlockedException {
        String ip = request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();

        if (loginAttemptsService.isBlocked(username, ip)) {
            throw new LoginAttemptBlockedException(MessagesEnum.LOGIN_ATTEMPTS_EXCEEDED_LIMIT.getMessage());
        }

        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var user = entityManager.createQuery(
                        "SELECT u FROM User u WHERE UPPER(u.username) = UPPER(:username)", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException(MessagesEnum.USER_NOT_FOUND.getMessage()));
        return new UserPrincipal(user);
    }
}
