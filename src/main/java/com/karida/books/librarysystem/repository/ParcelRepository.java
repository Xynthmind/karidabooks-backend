package com.karida.books.librarysystem.repository;

import org.springframework.stereotype.Repository;
import com.karida.books.librarysystem.models.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long>{
    @Query(value = "SELECT * FROM parcels WHERE company_name = ?1", nativeQuery = true)
    Parcel finByName(String company_name);
    @Query(value = "SELECT * FROM parcels WHERE company_name = ?1 AND id_parcel != ?2", nativeQuery = true)
    Parcel findTheNameInOtherCompanies(String company_name, Long id_parcel);
}
