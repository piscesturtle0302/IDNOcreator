package com.example.IDNOcreator.common.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name="IDNO_CREATOR")
public class IdnoCreator extends BasicEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "RES_IDNO")
    private String resIdno;

}
