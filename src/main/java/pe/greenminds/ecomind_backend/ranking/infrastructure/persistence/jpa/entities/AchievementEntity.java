package pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.AchievementType;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.AuditableAbstractPersistenceEntity;

@Entity
@Table(name = "achievements")
public class AchievementEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 30, nullable = false)
    private AchievementType type;

    @Column(name = "threshold_value")
    private Integer thresholdValue;

    protected AchievementEntity() {}

    public AchievementEntity(String name, String description, AchievementType type, Integer thresholdValue) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.thresholdValue = thresholdValue;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public AchievementType getType() { return type; }
    public Integer getThresholdValue() { return thresholdValue; }
}
