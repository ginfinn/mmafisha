package com.realityflex.mmafisha.dto.dtogetpost;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.realityflex.mmafisha.dto.dtopreview.Preview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class GetPost {
    Integer postId;
    String name;
    String title;
    String text;
    String jpgUrl;
    List<Preview> previews;
    List<CommentDto> commentDtos;
    Integer like;
    Date date;
}
