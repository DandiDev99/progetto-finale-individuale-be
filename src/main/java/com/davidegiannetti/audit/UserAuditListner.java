package com.davidegiannetti.audit;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserAuditListner {

    @PrePersist
    public void prePersist(Object entity) throws Exception{
        Class<?> cls = entity.getClass();
        for (Field field : cls.getDeclaredFields()){
            if(field.getAnnotation(CreatedDate.class)!=null) {
                Method setter = getSetter(field);
                setter.invoke(entity, LocalDate.now());
            }
            if(field.getAnnotation(LastModifiedDate.class)!=null){
                Method setter = getSetter(field);
                setter.invoke(entity, LocalDateTime.now());
            }
        }
    }

    @PreUpdate
    public void preUpdate(Object entity) throws Exception{
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()){
            if(field.getAnnotation(LastModifiedDate.class)!=null){
                Method setter = getSetter(field);
                setter.invoke(entity, LocalDateTime.now());
            }
        }
    }

    private Method getSetter(Field field) throws NoSuchMethodException {
        Class<?> c = field.getDeclaringClass();
        String nome = "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
        return c.getDeclaredMethod(nome, field.getType());
    }

}
