package pe.greenminds.ecomind_backend.shared.domain.model.valueobjects;

import jakarta.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractValueObject {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractValueObject that = (AbstractValueObject) o;
        return Objects.equals(this, that);
    }
    @Override
    public int hashCode() { return Objects.hash(this); }
}
