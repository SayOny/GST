package com.project.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.travel.domain.Place.Place;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue
    @Column(name="post_id")
    private Long id; //게시물 고유 인덱스

    private String title; //게시물 제목
    private Long like; //좋아요 개수
    private Long score; //게시물 평점63

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id")
    private User user; //게시물 작성자

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    private User likeUser;*/

    @ManyToMany
    @JoinTable(name="post_place",
            joinColumns = @JoinColumn(name="post_id"),
            inverseJoinColumns = @JoinColumn(name="place_id"))
    private List<Place> places=new ArrayList<>(); //게시물 내의 장소들


    @ManyToMany
    @JoinTable(name="post_tag",
            joinColumns = @JoinColumn(name="post_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id"))
    private List<Tag> tags= new ArrayList<>(); //게시물 내의 해시테그

    @ElementCollection
    private List<Integer> day=new ArrayList<>(); //몇 번째 날인지에 대한 정보
}
