package com.melimelo.validation;
import java.util.Collection;
public final class ValidationUtils {
        /**
     * check if an object is not null. If the object is null,
     * then an IllegalArgumentExceptionw ill be throw with the provided message.
     * @param object  the object to check.
     * @param message the message to of the exception, if thrown.
     */
    public static <T> void validateNotNull(final T object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static <T> void validateNoNeNull(final Collection<T> elements, String message) {
        for(T element : elements) {
            validateNotNull(element, message);
        }
    }
    public static <T> void validateNoNeNull(final String message, final T... elements) {
        for(T element : elements) {
            validateNotNull(element, message);
        }
    }
}