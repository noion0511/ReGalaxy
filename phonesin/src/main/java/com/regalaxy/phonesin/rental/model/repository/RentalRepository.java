package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.phone.model.entity.Phone;
import com.regalaxy.phonesin.rental.model.ApplyDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface RentalRepository extends JpaRepository<Rental, Long>, RentalRepositoryCustom {
    @Override
    Rental getReferenceById(Long aLong);

    @Override
    <S extends Rental> S save(S entity);

    @Override
    void deleteById(Long aLong);

    @Override
    List<RentalDetailDto> search(SearchDto searchDto);

    @Override
    boolean extension(int rental_id);

    @Override
    boolean apply(ApplyDto applyDto);
}
