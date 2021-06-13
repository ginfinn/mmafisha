package com.realityflex.mmafisha.dto.dtogetjson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class MyItem {
    Integer id;
    String title;
    String text;
    String date_from;
    String date_to;
    Integer date_from_timestamp;
    Integer date_to_timestamp;
    String status;
    Boolean free;
    Restriction restriction;
    Image image;
    List<Spot> spots;
    List<Sphere> spheres;
    List<District> districts;
    Foundation foundation;
    List<Auditorie> auditories;


}
