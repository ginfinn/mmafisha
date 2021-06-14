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
public class SetPost {
    String title;
    String text;
    String jpgUrl;
    List<Integer> itemId;
}
