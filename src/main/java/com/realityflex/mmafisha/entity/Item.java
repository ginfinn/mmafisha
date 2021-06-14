package com.realityflex.mmafisha.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Item {
    @Id

    Integer id;
    @Column(nullable = false, length = 9096)
    String title;
    @Column(nullable = false, length = 9096)
    String text;

    String dateFrom;

    String dateTo;

    Integer dateFromTimestamp;

    Integer dateToTimestamp;
    @Column(nullable = false, length = 9096)
    String status;
    Boolean free;

    int age;
    @Column(nullable = false, length = 9096)
    String ulrImage;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idItem")

    List<Spot> spots = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idItem")
    List<Sphere> spheres = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idItem")
    List<District> districts = new ArrayList<>();
    String foundationTitle;
    String phone;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idItem")
    List<Auditorie> auditories = new ArrayList<>();


}
