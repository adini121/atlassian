package com.atlassian.pageobjects.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class InjectUtils
{
    public static interface FieldVisitor<A extends Annotation>
    {
        void visit(Field field, A annotation);
    }
    public static <A extends Annotation> void forEachFieldWithAnnotation(Object instance, Class<A> annotation, FieldVisitor<A> fieldVisitor)
    {
        for (Field field : findAllFields(instance))
        {
            A ann = field.getAnnotation(annotation);
            if (ann != null)
            {
                fieldVisitor.visit(field, ann);
            }
        }
    }

    private static Collection<Field> findAllFields(Object instance)
    {
        Map<String,Field> fields = new HashMap<String,Field>();
        Class cls = instance.getClass();
        while (cls != Object.class)
        {
            for (Field field : cls.getDeclaredFields())
            {
                if (!fields.containsKey(field.getName()))
                {
                    fields.put(field.getName(), field);
                }
            }
            cls = cls.getSuperclass();
        }
        return fields.values();
    }
}
