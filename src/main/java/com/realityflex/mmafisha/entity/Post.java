package com.realityflex.mmafisha.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer postId;
    String title;
    String text;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    List<LinksUrl> linksUrl;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    List<IdItemForPost> idItemForPosts;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    List<Coment> coments;
    @Builder.Default
    Integer _likes=0;
    String  name;
    @Builder.Default
    Date date = new Date();
}
