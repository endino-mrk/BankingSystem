package main;

public interface FieldValidator<T, E> {
    public String validate(T value, E threshold);

}
