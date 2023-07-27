package com.regalaxy.phonesin.module.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ytwok{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String originalFileName;

    private String storedFileName;

    private long fileSize;

    @CreatedDate
    private LocalDateTime createdAt;
}