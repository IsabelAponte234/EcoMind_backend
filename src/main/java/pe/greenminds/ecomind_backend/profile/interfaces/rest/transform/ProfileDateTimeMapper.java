package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public final class ProfileDateTimeMapper {
    private ProfileDateTimeMapper() {}

    public static LocalDate toLocalDate(String value) {
        return value == null || value.isBlank() ? null : LocalDate.parse(value);
    }

    public static OffsetDateTime toOffsetDateTime(String value) {
        return value == null || value.isBlank() ? null : OffsetDateTime.parse(value);
    }

    public static String from(LocalDate value) {
        return value == null ? null : value.toString();
    }

    public static String from(OffsetDateTime value) {
        return value == null ? null : value.toString();
    }
}
