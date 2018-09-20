package com.cg.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;






public class AgeValidator implements ConstraintValidator<IsValidAge,Date> {

	@Override
	public void initialize(IsValidAge IsVAlidAge) {
		
		
	}

	@Override
	public boolean isValid(Date dob, ConstraintValidatorContext arg1) {
		if(dob!=null)
		{
		LocalDate ld=LocalDate.now();
		LocalDate date=dob.toLocalDate();
		Period p=Period.between(date,ld);
		System.out.println(p.getYears());
		if(p.getYears()<18||p.getYears()>58)
		{
			return false;
		}
		return true;
		}
		else 
			return true;
	}
	
}
