package com.regalaxy.phonesin.phone.model.repository;

import com.regalaxy.phonesin.phone.model.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
}
