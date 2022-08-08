package com.project.travel.domain.Place;

import com.project.travel.domain.Image;
import com.project.travel.domain.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@Getter
@Setter
public abstract class Place {
    @Id
    @GeneratedValue
    @Column(name="place_id")
    private Long id; //장소의 고유 인덱스

    private String name; //장소 이름
    private Long score; //평점
    private String content;//세부 내용

    @Embedded
    private Image image; //장소의 이미지

    @ManyToMany(mappedBy = "places")
    private List<Post> posts= new ArrayList<>();
}
