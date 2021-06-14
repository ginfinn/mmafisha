package com.realityflex.mmafisha.entity;

import lombok.*;

import javax.persistence.*;
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
    @Column(nullable = false, length = 9096)
    String title;
    @Column(nullable = false, length = 9096)
    String text;
    @Column(nullable = false, length = 9096)
    String jpgUrl;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    List<IdItemForPost> idItemForPosts;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    List<Comment> comments;
    @Builder.Default
    Integer _likes=0;
    String  name;
    @Builder.Default
    Date date = new Date();
}
