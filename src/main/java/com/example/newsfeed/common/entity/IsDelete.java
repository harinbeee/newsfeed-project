package com.example.newsfeed.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class IsDelete {

    @Column
    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }
}
