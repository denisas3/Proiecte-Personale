package org.example.restaurante.domain;

import java.util.Objects;

public class Entity<ID> {
    //private Long serialVersionUID;
    private static final Long serialVersionUID = 1L;
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    /*public Long getSerialVersionUID() {
        return serialVersionUID;
    }*/

    /*public void setSerialVersionUID(Long serialVersionUID) {
        this.serialVersionUID = serialVersionUID;
    }*/

    //adaugat de mine
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity<?> entity = (Entity<?>) o;
        return getId().equals(entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}