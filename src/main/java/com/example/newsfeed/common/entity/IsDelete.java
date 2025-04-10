package com.example.newsfeed.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@MappedSuperclass
public abstract class IsDelete {

    @Setter
    @Column
    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }
}
