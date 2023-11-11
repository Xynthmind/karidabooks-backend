package com.karida.books.librarysystem.repository;

import com.karida.books.librarysystem.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long> {
    @Query(value = "SELECT * FROM roles WHERE rol_name = ?1", nativeQuery = true)
    Rol findByName(String rol_name);

    @Query(value = "SELECT * FROM roles WHERE rol_number = ?1", nativeQuery = true)
    Rol findByNumber(Integer rol_number);
}
