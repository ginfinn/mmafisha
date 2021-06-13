package com.realityflex.mmafisha.dto.iteminfodto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.realityflex.mmafisha.entity.Spot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class ItemInfo {

    String title;
    String text;
    int date_to_timestamp;
    int date_from_timestamp;
    String date_from;
    String date_to;
    List<Spot> address;
    String phone;
    String jpgUrl;
}
