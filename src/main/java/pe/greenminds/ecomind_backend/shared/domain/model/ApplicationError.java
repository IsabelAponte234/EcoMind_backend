package pe.greenminds.ecomind_backend.shared.domain.model;

public record ApplicationError(String message, int code) {
    public static ApplicationError notFound(String resource, String identifier) {
        return new ApplicationError("%s with id %s was not found.".formatted(resource, identifier), 404);
    }

    public static ApplicationError conflict(String resource, String details) {
        return new ApplicationError("Conflict in %s: %s".formatted(resource, details), 409);
    }

    public static ApplicationError validationError(String resource, String details) {
        return new ApplicationError("Validation error in %s: %s".formatted(resource, details), 400);
    }

    public static ApplicationError unexpected(String resource, String details) {
        return new ApplicationError("Unexpected error in %s: %s".formatted(resource, details), 500);
    }
}
