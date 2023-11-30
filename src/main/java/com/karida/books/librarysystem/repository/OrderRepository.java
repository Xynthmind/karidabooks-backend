package com.karida.books.librarysystem.repository;

import com.karida.books.librarysystem.models.User;
import org.springframework.stereotype.Repository;
import com.karida.books.librarysystem.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    @Query(value = "SELECT * FROM orders WHERE id_user = ?1", nativeQuery = true)
    List<Order> findById_User(Long id);

    @Query(value = "SELECT * FROM orders WHERE id_user= ?1 ORDER BY id_order DESC LIMIT 1", nativeQuery = true)
    Order findTheLastOrder(Integer id_user);
}
