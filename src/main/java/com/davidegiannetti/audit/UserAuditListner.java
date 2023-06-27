package com.davidegiannetti.audit;

import jakarta.persistence.PrePersist;
import org.springframework.data.annotation.CreatedDate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;

public class UserAuditListner {

    @PrePersist
    public void prePersist(Object entity) throws Exception{
        Class<?> cls = entity.getClass();
        for (Field field : cls.getDeclaredFields()){
            Method setter = getSetter(field);
            if(field.getAnnotation(CreatedDate.class)!=null){
                setter.invoke(entity, LocalDate.now());
            }
        }
    }

    private Method getSetter(Field field) throws NoSuchMethodException {
        Class<?> c = field.getDeclaringClass();
        String nome = "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
        return c.getDeclaredMethod(nome, field.getType());
    }

}
