package com.realityflex.mmafisha.dto.putSpheredto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class PutSphereAndSubscriptionForUser {
    List<String> sphere;
    List<String> auditories;
    List<String> subscription;
}
