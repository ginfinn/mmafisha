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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    Role role;
    @OneToMany(mappedBy = "memberId",cascade = CascadeType.ALL)

    List<Subscriptions> subscriptions;
}
