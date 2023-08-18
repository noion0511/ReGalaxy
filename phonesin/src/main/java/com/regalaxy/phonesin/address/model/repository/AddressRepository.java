package com.regalaxy.phonesin.address.model.repository;

import com.regalaxy.phonesin.address.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>, AddressRepositoryCustom {
}
