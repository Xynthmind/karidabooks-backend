package com.karida.books.librarysystem.repository;
import com.karida.books.librarysystem.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long>{
    @Query(value = "SELECT * FROM ordersdetails WHERE id_order = ?1", nativeQuery = true)
    List<OrderDetails> finByOrder(Long id);
}
