package com.regalaxy.phonesin.member.model.entity;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String member_name;
    private String password;
    private String phone_number;
//    private Boolean isCha;
//    private Boolean isBlacklist;
//    private Boolean isDelete;
//    private Boolean isManager;

    @OneToMany(mappedBy = "member",cascade = ALL,orphanRemoval = true)
    private List<Donation> donationList = new ArrayList<Donation>();

}
