package team.mixxit.allotment.util;

import org.apache.commons.lang3.reflect.FieldUtils;

public class ReflectionHelper {
    public static void forceSetPrivateField(Object targetObject, String fieldName, Object value) throws IllegalAccessException, IllegalArgumentException {
        FieldUtils.writeField(targetObject, fieldName, value, true);
    }

    public static Object forceGetPrivateField(Object targetObject, String fieldName) throws IllegalAccessException, IllegalArgumentException {
        return FieldUtils.readField(targetObject, fieldName, true);
    }
}
