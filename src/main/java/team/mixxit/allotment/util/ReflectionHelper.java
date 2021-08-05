package team.mixxit.allotment.util;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ReflectionHelper {
    public static void forceSetPrivateField(Object targetObject, String fieldName, Object value) throws IllegalAccessException, IllegalArgumentException {
        FieldUtils.writeField(targetObject, fieldName, value, true);
    }

    public static Object forceGetPrivateField(Object targetObject, String fieldName) throws IllegalAccessException, IllegalArgumentException {
        return FieldUtils.readField(targetObject, fieldName, true);
    }

    public static Object clone(Object source){
        try {
            ByteArrayOutputStream outByte = new ByteArrayOutputStream();
            ObjectOutputStream outObj = new ObjectOutputStream(outByte);
            ByteArrayInputStream inByte;
            ObjectInputStream inObject;
            outObj.writeObject(source);
            outObj.close();
            byte[] buffer = outByte.toByteArray();
            inByte = new ByteArrayInputStream(buffer);
            inObject = new ObjectInputStream(inByte);
            @SuppressWarnings("unchecked")
            Object deepcopy =  inObject.readObject();
            inObject.close();
            return deepcopy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
