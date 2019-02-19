package de.jsauer.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class SecurityConfigTest {

    /**
     * Tests if the length if the two arrays containing the allowed navigation targets and the corresponding regex match.
     * The number of entries should always be the same.
     */
    @Test
    void allowedClassesTest() {
        try {
            //Access the two arrays via reflection because they are private.
            Field allowedClassesField = SecurityConfig.class.getDeclaredField("ALLOWED_CLASSES");
            allowedClassesField.setAccessible(true);
            Class[] allowedClasses = (Class[])allowedClassesField.get(allowedClassesField.getType());

            Field allowedClassesRegexField = SecurityConfig.class.getDeclaredField("ALLOWED_CLASSES_REGEX");
            allowedClassesRegexField.setAccessible(true);
            String[] allowedClassesRegex = (String[])allowedClassesRegexField.get(allowedClassesRegexField.getType());

            Assertions.assertEquals(allowedClasses.length ,
                    allowedClassesRegex.length,
                    "Length of allowed classes arrays does not match!");
        } catch (NoSuchFieldException e) {
            Assertions.fail("Not all fields could be found!");
        } catch (IllegalAccessException e) {
            Assertions.fail("Not all fields could be accessed!");
        }

    }

}