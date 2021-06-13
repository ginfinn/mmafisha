package com.realityflex.mmafisha.dto.dtofordate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class Date {
    String date;
    List<String> sphere;
    Boolean free;
    String title;
    Integer date_from_timestamp;
    int idItem;


}
