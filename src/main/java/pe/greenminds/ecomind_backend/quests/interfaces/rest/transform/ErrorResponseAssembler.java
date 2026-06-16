package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.greenminds.ecomind_backend.shared.domain.model.ApplicationError;

public final class ErrorResponseAssembler {
    private ErrorResponseAssembler() {}

    public static ResponseEntity<?> toErrorResponseFromApplicationError(ApplicationError error) {
        HttpStatus status = HttpStatus.valueOf(error.code());
        return new ResponseEntity<>(error, status);
    }
}
