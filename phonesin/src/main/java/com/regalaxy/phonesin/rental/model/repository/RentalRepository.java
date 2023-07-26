package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface RentalRepository extends JpaRepository<Rental, Long>, RentalRepositoryCustom {
    @Query()
    void deleteById(int rental_id);

    @Override
    String search(SearchDto searchDto);
}
