package com.hahn.stock.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base entity with common audit fields.
 */
@MappedSuperclass //fields will be mapped to the entities that inherit from it.
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder(builderMethodName = "builder")
@NoArgsConstructor
public abstract class BaseEntity implements Serializable {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false, updatable = false, name = "created_date")
    @CreatedDate
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;

    @Column(nullable = false, name = "updated_date")
    @LastModifiedDate
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @PreUpdate
    protected void onUpdate() {
        if (this.createdBy == null) {
            this.createdBy = this.updatedBy;
        }
    }


    /**
     * Get the unique identifier of the entity.
     *
     * @return The entity's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the unique identifier of the entity.
     *
     * @param id The ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the date and time when the entity was created.
     *
     * @return The creation date.
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Set the date and time when the entity was created.
     *
     * @param createdDate The creation date to set.
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get the date and time when the entity was last updated.
     *
     * @return The last updated date.
     */
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set the date and time when the entity was last updated.
     *
     * @param updatedDate The last updated date to set.
     */
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Get the user who created the entity.
     *
     * @return The creator's username.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Set the user who created the entity.
     *
     * @param createdBy The creator's username to set.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Get the user who last updated the entity.
     *
     * @return The username of the last updater.
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Set the user who last updated the entity.
     *
     * @param updatedBy The username of the last updater to set.
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
