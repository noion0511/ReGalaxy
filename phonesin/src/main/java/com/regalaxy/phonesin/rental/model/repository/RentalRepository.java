package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.rental.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long>, RentalRepositoryCustom {
}
