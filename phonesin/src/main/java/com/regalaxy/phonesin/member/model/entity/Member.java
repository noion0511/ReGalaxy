package com.regalaxy.phonesin.member.model.entity;

import com.regalaxy.phonesin.rental.model.entity.Rental;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name="member")
@Table(name="member")
public class Member {
    @Id
    private int member_id;
    @OneToMany(mappedBy = "member")
    private List<Rental> Rental;
//    private List<Member> member_id = new ArrayList<>();
}
