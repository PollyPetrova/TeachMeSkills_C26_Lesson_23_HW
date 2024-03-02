package org.by.tms.core;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Operation {
	private final double num1;
	private final double num2;
	private final String type;
	private double result;

}