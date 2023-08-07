package com.regalaxy.phonesin.member.model.entity;

import com.regalaxy.phonesin.address.model.entity.Address;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.global.BaseTimeEntity;
import com.regalaxy.phonesin.member.model.MemberAdminDto;
import com.regalaxy.phonesin.member.model.MemberUserDto;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @ApiModelProperty(value = "멤버 ID")
    private Long memberId;

    @Column(unique = true)
    @ApiModelProperty(value = "사용자 이메일")
    private String email;

    @ApiModelProperty(value = "사용자 이름")
    private String memberName;

    @ApiModelProperty(value = "비밀번호")
    private String password;

    @ApiModelProperty(value = "휴대폰 번호")
    private String phoneNumber;

    @ApiModelProperty(value = "차상위 계층 여부")
    private Boolean isCha;

    @ApiModelProperty(value = "블랙리스트 여부")
    private Boolean isBlackList;

    @ApiModelProperty(value = "사용자 아이디 삭제 여부")
    private Boolean isDelete;

    @ApiModelProperty(value = "관리자 권한 여부")
    private Boolean isManager;

    @ApiModelProperty(value = "리프레시 토큰")
    private String refreshToken;

    @Builder
    public Member(Long memberId, String email, String memberName, String password, String phoneNumber, Boolean isCha, Boolean isBlackList, Boolean isDelete, Boolean isManager, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.email = email;
        this.memberName = memberName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.isCha = isCha;
        this.isBlackList = isBlackList;
        this.isDelete = isDelete;
        this.isManager = isManager;
    }

    @ApiModelProperty(value = "기부 리스트")
    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Donation> donationList = new ArrayList<Donation>();

    @ApiModelProperty(value = "대여 리스트")
    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Rental> rentalList = new ArrayList<>();

    @ApiModelProperty(value = "주소 리스트")
    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Address> addressList = new ArrayList<>();

    public static MemberUserDto toUserDto(Member member) {
        return MemberUserDto.builder()
                .memberName(member.getMemberName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .isCha(member.getIsCha())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    public static MemberAdminDto toAdminDto(Member member) {
        return MemberAdminDto.builder()
                .memberId(member.getMemberId())
                .memberName(member.getMemberName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .isCha(member.getIsCha())
                .createdAt(member.getCreatedAt())
                .isBlackList(member.getIsBlackList())
                .isDelete(member.getIsDelete())
                .isManager(member.getIsManager())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    // 리프레시 토큰 업데이트
    // 이미 리프레시 토큰이 있어도 업데이트 됨.
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // 관리자가 회원 정보 변경
    public void updateByAdmin(MemberAdminDto memberAdminDto) {
        this.memberName = memberAdminDto.getMemberName();
        this.email = memberAdminDto.getEmail();
        this.phoneNumber = memberAdminDto.getPhoneNumber();
        this.isCha = memberAdminDto.getIsCha();
        this.isManager = memberAdminDto.getIsManager();
        this.isBlackList = memberAdminDto.getIsBlackList();
        this.isDelete = memberAdminDto.getIsDelete();
    }

    // isDelete를 true로 변경, 리프레시 토큰 초기화
    public void delete() {
        this.isDelete = true;
        this.refreshToken = null;
    }

    // 사용자가 자신의 정보 변경
    public void updateByUser(MemberUserDto memberUserDto) {
        this.memberName = memberUserDto.getMemberName();
        this.email = memberUserDto.getEmail();
        this.phoneNumber = memberUserDto.getPhoneNumber();
    }

    // 비밀번호 변경
    public void updatePassword(String encode) {
        this.password = encode;
    }
}