package com.edug.devfinder.services;

import com.edug.devfinder.messages.MessagesEnum;
import com.edug.devfinder.models.entities.User;
import com.edug.devfinder.models.entities.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserDetailsServiceImpl implements UserDetailsService {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
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
