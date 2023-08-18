package com.regalaxy.phonesin.notice.model.entity;

import com.regalaxy.phonesin.global.BaseTimeEntity;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long noticeId;

    @OneToOne
    @JoinColumn(name="member_id")
    Member member;
    String title;
    String posterUrl;
    Integer status; // 1: 예정, 2: 진행중, 3: 종료
    Integer noticeType; // 1: 배너 , 2: 바텀 공지

    @Builder
    public Notice(Member member, String title, String posterUrl, Integer status, Integer noticeType) {
        this.member = member;
        this.title = title;
        this.posterUrl = posterUrl;
        this.status = status;
        this.noticeType = noticeType;
    }
}
