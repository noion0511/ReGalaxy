package com.regalaxy.phonesin.phone.model.entity;

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
    private Long modelId;

    private String modelName;
    private String nickname;
}
