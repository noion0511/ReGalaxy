package com.regalaxy.phonesin.address.model.repository;

import com.regalaxy.phonesin.address.model.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, Long>, AddressRepositoryCustom {
}
