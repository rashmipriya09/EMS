package com.cg.service;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SelectValidator implements ConstraintValidator<IsValidSelect,String>{

	@Override
	public void initialize(IsValidSelect arg0) {

		
	}

	@Override
	public boolean isValid(String maritalStatus,ConstraintValidatorContext arg1) {
		if(maritalStatus.equals("select Marital Status")||maritalStatus.equals("Select Grade"))
		return false;
		else 
		return true;
	}

	

}
