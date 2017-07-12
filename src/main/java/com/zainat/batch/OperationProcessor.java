package com.zainat.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.zainat.model.Operation;

@Component
public class OperationProcessor implements ItemProcessor<Operation, Operation> {

	@Override
	public Operation process(Operation arg0) throws Exception {
		System.out.println("*** Mon processor ***");
		return null;
	}
}
