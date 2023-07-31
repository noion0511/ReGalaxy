package com.regalaxy.phonesin.address.model.entity;

import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "address")
@Getter
@Setter
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long address_id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String address;
}
