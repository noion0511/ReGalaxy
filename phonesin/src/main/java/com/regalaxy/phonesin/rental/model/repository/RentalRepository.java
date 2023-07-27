package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.ApplyDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long>, RentalRepositoryCustom {
    @Override
    Rental getReferenceById(Long aLong);

    @Override
    <S extends Rental> S save(S entity);

    @Override
    void deleteById(Long aLong);

    @Override
    List<RentalDto> search(SearchDto searchDto);

    @Override
    boolean extension(int rental_id);

    @Override
    boolean apply(ApplyDto applyDto);

    @Override
    RentalDetailDto detailInfo(int rental_id);
}
