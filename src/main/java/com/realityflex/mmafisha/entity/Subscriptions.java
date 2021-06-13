package com.realityflex.mmafisha.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Subscriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String sphere;
}
