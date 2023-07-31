package com.regalaxy.phonesin.member.model.entity;

import com.regalaxy.phonesin.address.model.entity.Address;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(unique = true)
    private String email;

    private String authority;
    private String memberName;
    private String password;
    private String phoneNumber;
    private Boolean isCha;
    private Boolean isBlackList;
    private Boolean isDelete;
    private Boolean isManager;

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Donation> donationList = new ArrayList<Donation>();

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Rental> rentalList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Address> addressList = new ArrayList<>();
}