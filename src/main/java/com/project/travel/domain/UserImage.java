package com.project.travel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserImage{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_image_id")
    private Long id;

    @Embedded
    private Image image;

    @JsonIgnore
    @OneToOne(mappedBy = "userImage",fetch = FetchType.LAZY)
    private User user;
}