package com.karida.books.librarysystem.repository;
import com.karida.books.librarysystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    User findByEmail(String email);
    @Query(value = "SELECT * FROM users WHERE email = ?1 AND id_user != ?2", nativeQuery = true)
    User findTheEmailInOtherUser(String email, Long id_user);
}
