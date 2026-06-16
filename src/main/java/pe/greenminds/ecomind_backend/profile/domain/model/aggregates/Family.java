package pe.greenminds.ecomind_backend.profile.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.util.Objects;

public class Family extends AbstractDomainAggregateRoot<Family> {
    @Getter
    @Setter
    private Long id;

    @Getter
    private String name;
    @Getter
    private String commitment;

    public Family(Long id, String name, String commitment) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.commitment = commitment;
    }

    public void update(String name, String commitment) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.commitment = commitment;
    }

    public void updateCommitment(String commitment) {
        this.commitment = commitment;
    }
}
