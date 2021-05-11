package com.romanyuk.data.db.converter;

public interface Converter<T, V> {
    V from(final T t);

    T to(final V v);
}
