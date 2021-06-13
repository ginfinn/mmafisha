package com.realityflex.mmafisha.dto.dtogetjson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.realityflex.mmafisha.dto.dtogetjson.Big;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class Image {
   Big small;
}
