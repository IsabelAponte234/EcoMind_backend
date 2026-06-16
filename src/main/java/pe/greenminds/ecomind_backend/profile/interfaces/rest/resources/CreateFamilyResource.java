package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record CreateFamilyResource(@NotBlank String name, String commitment) {
}
