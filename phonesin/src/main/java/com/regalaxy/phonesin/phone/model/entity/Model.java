package com.regalaxy.phonesin.phone.model.entity;

import com.regalaxy.phonesin.address.model.entity.Agency;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="model")
@Getter
@Setter
@Table(name="model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private int model_id;

    private String model_name;
    private String nickname;
}
