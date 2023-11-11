package com.karida.books.librarysystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.karida.books.librarysystem.models.Address;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
    @Query(value = "SELECT * FROM addresses WHERE id_user = ?1", nativeQuery = true)
    List<Address> findById_User(Long id);
}
