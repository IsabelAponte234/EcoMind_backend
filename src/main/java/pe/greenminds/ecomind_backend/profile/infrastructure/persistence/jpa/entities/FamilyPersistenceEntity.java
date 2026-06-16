package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

@Entity
@Table(name = "families")
public class FamilyPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false)
    private String name;
    @Column(length = 1000)
    private String commitment;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCommitment() { return commitment; }
    public void setCommitment(String commitment) { this.commitment = commitment; }
}
