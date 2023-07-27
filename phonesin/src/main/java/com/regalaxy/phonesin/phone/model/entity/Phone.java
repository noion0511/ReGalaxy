package com.regalaxy.phonesin.phone.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="phone")
@Table(name="phone")
public class Phone {
    @Id
    private int phone_id;
    private int rental_id;
    private int model_id;
}
