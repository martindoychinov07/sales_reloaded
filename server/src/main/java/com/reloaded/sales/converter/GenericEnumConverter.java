package com.reloaded.sales.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.lang.reflect.ParameterizedType;
import java.util.stream.Stream;

/**
 * A generic JPA converter for enums implementing PersistableEnum<T>.
 * Works automatically with @Converter(autoApply = true).
 */
@Converter(autoApply = true)
@SuppressWarnings("unchecked")
public class GenericEnumConverter<E extends Enum<E> & PersistableEnum<T>, T>
  implements AttributeConverter<E, T> {

  private final Class<E> enumClass;

  public GenericEnumConverter() {
    // Try to infer the enum type at runtime (works if subclassed, or JPA provider can inject)
    this.enumClass = (Class<E>) inferEnumClass();
  }

  @Override
  public T convertToDatabaseColumn(E attribute) {
    return attribute != null ? attribute.getValue() : null;
  }

  @Override
  public E convertToEntityAttribute(T dbData) {
    if (dbData == null || enumClass == null) {
      return null;
    }

    return Stream.of(enumClass.getEnumConstants())
      .filter(e -> e.getValue().equals(dbData))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException(
        "Unknown database value " + dbData + " for enum " + enumClass.getSimpleName()));
  }

  private Class<?> inferEnumClass() {
    try {
      var type = getClass().getGenericSuperclass();
      if (type instanceof ParameterizedType parameterizedType) {
        var actualType = parameterizedType.getActualTypeArguments()[0];
        if (actualType instanceof Class<?> c) {
          return c;
        }
      }
    } catch (Exception ignored) { }
    return null;
  }
}