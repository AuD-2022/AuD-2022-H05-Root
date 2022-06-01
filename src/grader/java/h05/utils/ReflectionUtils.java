package h05.utils;

import org.opentest4j.AssertionFailedError;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.Arrays;
import java.util.stream.Stream;

public class ReflectionUtils {

    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            Field field = Stream.concat(Arrays.stream(clazz.getFields()), Arrays.stream(clazz.getDeclaredFields()))
                .filter(f -> f.getName().equals(fieldName))
                .findAny()
                .orElseThrow(() ->
                    new AssertionFailedError("Unable to locate field %s in class %s".formatted(fieldName, clazz.getName())));
            field.setAccessible(true);
            return field;
        } catch (InaccessibleObjectException e) {
            throw new AssertionFailedError("Unable to access field %s in class %s".formatted(fieldName, clazz.getName()), e);
        }
    }

    public static <T, R> R getFieldValue(Class<T> clazz, String fieldName, T instance) {
        return getFieldValue(getField(clazz, fieldName), instance);
    }

    @SuppressWarnings("unchecked")
    public static <T, R> R getFieldValue(Field field, T instance) {
        try {
            return (R) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
