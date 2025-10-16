package ru.nersus.stock.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nersus.stock.entity.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = ?1 and u.password = ?2")
    Optional<User> findByEmailAndLogin(String email, String password);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}
