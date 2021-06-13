package com.realityflex.mmafisha.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Coment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String message;
    String name;
}
