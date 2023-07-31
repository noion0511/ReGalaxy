package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.ApplyDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long>, RentalRepositoryCustom {
}
