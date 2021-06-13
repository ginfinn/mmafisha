package com.realityflex.mmafisha.dto.dtopreview;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class Preview {
    int idItem;
    String date;
    String title;
    String jpgUrl;
    List<String> sphere;
    Double lat;
    Double lon;
    Integer date_from_timestamp;
    Boolean free;
    String address;
}
