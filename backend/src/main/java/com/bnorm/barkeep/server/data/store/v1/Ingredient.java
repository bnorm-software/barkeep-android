package com.bnorm.barkeep.server.data.store.v1;

import java.util.Set;

import com.bnorm.barkeep.measurement.UnitType;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Ingredient {

    @Id private String name;
    @Index private Set<String> nameWords;
    private UnitType type;

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

    public UnitType getType() {
        return type;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Ingredient that = (Ingredient) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (nameWords != null ? !nameWords.equals(that.nameWords) : that.nameWords != null) {
            return false;
        }
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (nameWords != null ? nameWords.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
