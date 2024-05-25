package Builders;

public interface Builder<T,B> {
    T build();
    B end();
}
