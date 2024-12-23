package com.example.uni_dubna.util;


import com.example.uni_dubna.models.ScientificUser;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ScientificUserValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return ScientificUser.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        
    }
}
