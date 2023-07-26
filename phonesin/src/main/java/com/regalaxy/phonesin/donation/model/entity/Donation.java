package com.regalaxy.phonesin.donation.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "donation")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Long id;

    private int donation_status;

    @CreatedDate
    @Column(name = "donation_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private String donation_delivery_date;

    private String donation_delivery_location_type;

    private String donation_delivery_location;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Donation(Member member, int donation_status, String donation_delivery_date, String donation_delivery_location_type, String donation_delivery_location) {
        this.member = member;
        this.donation_status = donation_status;
        this.donation_delivery_date = donation_delivery_date;
        this.donation_delivery_location_type = donation_delivery_location_type;
        this.donation_delivery_location = donation_delivery_location;
    }
}
