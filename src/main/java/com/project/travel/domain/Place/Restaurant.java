package com.project.travel.domain.Place;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("R")
@Getter
@Setter
public class Restaurant extends Place{
}
