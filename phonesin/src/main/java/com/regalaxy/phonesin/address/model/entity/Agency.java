package com.regalaxy.phonesin.address.model.entity;

import com.regalaxy.phonesin.rental.model.entity.Rental;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name="agency")
@Table(name="agency")
public class Agency {
    @Id
    private int agency_id;
    @OneToMany(mappedBy = "agency")
    private List<Rental> rental;
//    private List<Agency> agency_id = new ArrayList<>();
}
