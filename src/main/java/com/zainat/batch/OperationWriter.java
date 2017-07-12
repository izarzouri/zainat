package com.zainat.batch;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.zainat.model.Operation;

@Component
@Transactional
public class OperationWriter implements ItemWriter<Operation> {

	@Override
	public void write(List<? extends Operation> arg0) throws Exception {
		System.out.println("*** Mon writer ***");
	}

}
