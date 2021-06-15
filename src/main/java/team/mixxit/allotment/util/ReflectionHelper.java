package team.mixxit.allotment.util;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

public class ReflectionHelper {
    public static Field getField(Class targetClass, String fieldName) throws NoSuchFieldException {
        try {
            return targetClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = targetClass.getSuperclass();
            if (superClass == null) {
                throw e;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }

    public static void forceSetPrivateField(Object targetObject, String fieldName, Object value) throws IllegalAccessException {
        FieldUtils.writeField(targetObject, fieldName, value, true);
        /*Field field = getField(targetObject.getClass(), fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);*/
    }
}
