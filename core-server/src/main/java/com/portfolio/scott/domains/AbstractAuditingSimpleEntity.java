 package com.portfolio.scott.domains;

 import com.fasterxml.jackson.annotation.JsonIgnore;
 import jakarta.persistence.Column;
 import jakarta.persistence.EntityListeners;
 import jakarta.persistence.MappedSuperclass;
 import org.springframework.data.annotation.CreatedBy;
 import org.springframework.data.annotation.CreatedDate;
 import org.springframework.data.jpa.domain.support.AuditingEntityListener;

 import java.io.Serializable;
 import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingSimpleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private Instant createdDate = Instant.now();

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

}
