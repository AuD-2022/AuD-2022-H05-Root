package h05.utils;

import org.opentest4j.AssertionFailedError;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionUtils {

    private static final Map<Class<?>, Map<String, Field>> FIELD_CACHE = new HashMap<>();
    private static final Map<Class<?>, Map<String, Method>> METHOD_CACHE = new HashMap<>();

    private static final Function<Method, String> GET_METHOD_SIGNATURE = method -> "%s(%s)".formatted(method.getName(),
        Arrays.stream(method.getParameterTypes())
            .map(Class::getName)
            .collect(Collectors.joining(", "))
    );

    public static Field getField(Class<?> clazz, String fieldName) {
        return FIELD_CACHE.computeIfAbsent(clazz, c -> new HashMap<>())
            .computeIfAbsent(fieldName, s -> {
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
            });
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

    public static Method getMethodByName(Class<?> clazz, String methodName) {
         Method[] matchingCachedMethods = METHOD_CACHE.computeIfAbsent(clazz, c -> new HashMap<>())
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey().startsWith(methodName))
            .map(Map.Entry::getValue)
            .toArray(Method[]::new);

         if (matchingCachedMethods.length == 0) {
             Method[] methods = Stream.concat(Arrays.stream(clazz.getMethods()), Arrays.stream(clazz.getDeclaredMethods()))
                 .filter(method -> method.getName().equals(methodName))
                 .toArray(Method[]::new);

             if (methods.length == 0) {
                 throw new AssertionFailedError("No method with name \"%s\" found in class %s".formatted(methodName, clazz.getName()));
             } else if (methods.length > 1) {
                 throw new AssertionFailedError("Cannot resolve ambiguity: Found %d methods in class %s with name %s"
                     .formatted(methods.length, clazz.getName(), methodName));
             } else {
                 METHOD_CACHE.get(clazz).put(GET_METHOD_SIGNATURE.apply(methods[0]), methods[0]);
                 return methods[0];
             }
         } else if (matchingCachedMethods.length > 1) {
             throw new RuntimeException("Method name %s is ambiguous; found %d method with the same name".formatted(methodName,
                 matchingCachedMethods.length));
         } else {
             return matchingCachedMethods[0];
         }
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... argumentTypes) {
        String signature = "%s(%s)".formatted(methodName,
            Arrays.stream(argumentTypes)
                .map(Class::getName)
                .collect(Collectors.joining(", "))
        );

        return METHOD_CACHE.computeIfAbsent(clazz, c -> new HashMap<>())
            .computeIfAbsent(signature, s -> Stream.concat(Arrays.stream(clazz.getMethods()),
                    Arrays.stream(clazz.getDeclaredMethods()))
                .filter(method -> method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), argumentTypes))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Could not find any methods matching signature " + signature))
            );
    }
}
