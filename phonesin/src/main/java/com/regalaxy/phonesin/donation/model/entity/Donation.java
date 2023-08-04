package com.regalaxy.phonesin.donation.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.regalaxy.phonesin.global.BaseTimeEntity;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "donation")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "donation")
public class Donation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Long donationId;

    private int donationStatus;

    private String donationDeliveryDate;

    private String donationDeliveryLocationType;

    private String donationDeliveryLocation;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Donation(Member member, int donation_status, String donation_delivery_date, String donation_delivery_location_type, String donation_delivery_location) {
        this.member = member;
        this.donationStatus = donation_status;
        this.donationDeliveryDate = donation_delivery_date;
        this.donationDeliveryLocationType = donation_delivery_location_type;
        this.donationDeliveryLocation = donation_delivery_location;
    }
}
