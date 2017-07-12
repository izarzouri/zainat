package com.zainat.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import com.zainat.model.Operation;

@Component
@StepScope
public class OperationReader implements ItemReader<Operation>{

	@Override
	public Operation read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		System.out.println("*** mon Reader ***");
		return null;
	}

}
