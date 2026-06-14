package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

@Entity
@Table(name = "communities")
public class CommunityPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_count", nullable = false)
    private int userCount;

    @Column(name = "location", nullable = false)
    private String location;

    public String getName() { return name; }
    public int getUserCount() { return userCount; }
    public String getLocation() { return location; }

    public void setName(String name) { this.name = name; }
    public void setUserCount(int userCount) { this.userCount = userCount; }
    public void setLocation(String location) { this.location = location; }
}
