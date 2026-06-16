package pe.greenminds.ecomind_backend.profile.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Locale;

public enum FriendStatus {
    PENDING("pending"),
    ACCEPTED("accepted"),
    REJECTED("rejected");

    private final String value;

    FriendStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static FriendStatus from(String value) {
        if (value == null || value.isBlank()) {
            return PENDING;
        }
        return FriendStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
    }
}
