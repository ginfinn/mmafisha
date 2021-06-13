package com.realityflex.mmafisha.dto.dtogetjson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class Spot {
    String address;
    Double lat;
    Double lon;
    int foundation_id;
}
