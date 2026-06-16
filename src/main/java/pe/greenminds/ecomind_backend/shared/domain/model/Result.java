package pe.greenminds.ecomind_backend.shared.domain.model;

import java.util.Optional;

public class Result<T, E> {

    private final T data;
    private final E error;

    private Result(T data, E error) {
        this.data = data;
        this.error = error;
    }

    public static <T, E> Result<T, E> ok(T data) {
        return new Result<>(data, null);
    }

    public static <T, E> Result<T, E> success(T data) {
        return ok(data);
    }

    public static <T, E> Result<T, E> error(E error) {
        return new Result<>(null, error);
    }

    public static <T, E> Result<T, E> failure(E error) {
        return error(error);
    }

    public boolean isSuccess() { return data != null; }
    public boolean isFailure() { return error != null; }
    public T getData() { return data; }
    public E getError() { return error; }

    public Optional<T> getOptionalData() { return Optional.ofNullable(data); }
    public Optional<E> getOptionalError() { return Optional.ofNullable(error); }
}
