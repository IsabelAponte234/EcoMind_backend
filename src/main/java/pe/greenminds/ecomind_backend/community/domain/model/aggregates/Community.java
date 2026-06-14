package pe.greenminds.ecomind_backend.community.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

public class Community extends AbstractDomainAggregateRoot<Community> {
    @Getter
    @Setter
    private Long id;

    private String name;
    private int userCount;
    private String location;

    public Community(Long id, String name, int userCount, String location) {
        this.id = id;
        this.name = name;
        this.userCount = userCount;
        this.location = location;
    }

    public Community(String name, int userCount, String location) {
        this(null, name, userCount, location);
    }
    public Community(String name, String location) {this(null, name, 0, location);}

    public String getName() {
        return name;
    }
    public int getUserCount() {
        return userCount;
    }
    public String getLocation() {
        return location;
    }


}
