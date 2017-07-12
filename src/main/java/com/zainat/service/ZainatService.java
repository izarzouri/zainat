package com.zainat.service;

import java.text.ParseException;

import com.zainat.model.Operation;

public interface ZainatService {

	public void process() throws ParseException;

	public String toCSV(Operation op);
}
