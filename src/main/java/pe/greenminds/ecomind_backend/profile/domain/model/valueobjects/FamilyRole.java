package pe.greenminds.ecomind_backend.profile.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum FamilyRole {
    PARENT("parent"),
    CHILD("child");

    private final String value;

    FamilyRole(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static FamilyRole from(String value) {
        if (value == null || value.isBlank()) {
            return CHILD;
        }
        return FamilyRole.valueOf(value.trim().toUpperCase(Locale.ROOT));
    }
}
