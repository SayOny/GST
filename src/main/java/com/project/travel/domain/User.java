package com.project.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    private String userName;
    private String userId;
    private String userPassword;
    private String userPasswordCheck;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> posts=new ArrayList<>();

/*    @OneToMany(mappedBy = "likeUser", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<Post> likePosts=new ArrayList<>();*/

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_image_id")
    private UserImage userImage;
}