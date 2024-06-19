package com.aakarsh09z.registrationapi.Exceptions;

import lombok.Getter;
import lombok.Setter;

/*
    This class is a custom exception which extends runtime error and is thrown when any resource is not found in the database.
 */

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    long fieldValue;
    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
