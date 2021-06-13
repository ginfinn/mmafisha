package com.realityflex.mmafisha.dto.dotforuserupdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class UserUpdate {
    String name;
    String avatarUrl;
    String text;
}
