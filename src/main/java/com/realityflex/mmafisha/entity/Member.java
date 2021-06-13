package com.realityflex.mmafisha.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    Integer memberId;
    String memberName;
    String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
    @OneToMany
    @JoinColumn(name = "member_id")
    List<Subscriptions> subscriptions;
}
