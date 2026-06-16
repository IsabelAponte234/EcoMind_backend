package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.greenminds.ecomind_backend.shared.domain.model.ApplicationError;
import pe.greenminds.ecomind_backend.shared.domain.model.Result;

import java.util.function.Function;

public final class ResponseEntityAssembler {
    private ResponseEntityAssembler() {}

    public static <T, R> ResponseEntity<?> toResponseEntityFromResult(
            Result<T, ApplicationError> result,
            Function<T, R> mapper,
            HttpStatus successStatus
    ) {
        if (result.isSuccess()) {
            var resource = mapper.apply(result.getData());
            return new ResponseEntity<>(resource, successStatus);
        }
        var error = result.getError();
        HttpStatus status = HttpStatus.valueOf(error.code());
        return new ResponseEntity<>(error, status);
    }
}
