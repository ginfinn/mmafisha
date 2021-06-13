package com.realityflex.mmafisha.dto.dtoforsetpost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class Setpost {
    String title;
    String text;
    List<String> jpgUrl;
    List<Integer> itemId;
}
