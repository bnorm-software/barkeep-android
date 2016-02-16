package com.bnorm.barkeep.server.data.store.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.mapper.Mapper;

@Entity
public class Recipe {

    @Id private String name;
    @Index private Set<String> nameWords;
    private String picture;
    private String description;
    private List<Component> components = new ArrayList<>();
    private String directions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getNameWords() {
        return nameWords;
    }

    public void setNameWords(Set<String> nameWords) {
        this.nameWords = nameWords;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }
}
