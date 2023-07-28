package com.regalaxy.phonesin.back.model.repository;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

public interface BackRepository extends JpaRepository<Back, Long> {

    @Query("select b from back b join b.rental r join r.member m where m.email = :email")
    Page<Back> findAllByEmail(@Param("email") String email, Pageable pageable);

    @Query("select b from back b join b.rental r join r.member m")
    Page<Back> findAll(Pageable pageable);

    @Query("select b from back b join b.rental r join r.member m where m.isBlackList = :isBlack")
    Page<Back> findAllByIsBlack(@Param("isBlack") boolean isBlack, Pageable pageable);

    @Query("select b from back b join b.rental r join r.member m where m.isCha = :isCha")
    Page<Back> findAllByIsCha(@Param("isCha") boolean isCha, Pageable pageable);
}
