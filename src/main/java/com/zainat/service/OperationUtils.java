//package com.zainat.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.zainat.model.Operation;
//
//@Component
//public class OperationUtils {
//
//	@Value("${zainat.compta.operation.csv.separator:;}")
//	private static String csvSeparator;
//
//	public OperationUtils() {
//
//	}
//
//	public String toCSV(Operation op) {
//		return op.getDateValeur() + csvSeparator + op.getLibelle() + csvSeparator + op.getMontant() + csvSeparator
//				+ op.getTva() + csvSeparator + op.getMontantHT() + csvSeparator;
//	}
//}
