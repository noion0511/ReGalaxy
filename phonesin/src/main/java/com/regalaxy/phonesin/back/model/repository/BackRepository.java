package com.regalaxy.phonesin.back.model.repository;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public interface BackRepository extends JpaRepository<Back, Long> {


}
