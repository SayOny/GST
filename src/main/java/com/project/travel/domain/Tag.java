package com.project.travel.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long id; //해시테그 고유 아이디

    private String tagContent; //해시태그 내용

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts=new ArrayList<>(); //해시태그가 포함된 게시물들
}
