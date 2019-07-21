/* fake-data-maker : Generate fake data for machine learning
 *
 *  Copyright (c) 2019 Tom Misawa, riversun.org@gmail.com
 *  
 *  Permission is hereby granted, free of charge, to any person obtaining a
 *  copy of this software and associated documentation files (the "Software"),
 *  to deal in the Software without restriction, including without limitation
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 *  DEALINGS IN THE SOFTWARE.
 *  
 */
package org.riversun.ml.fakedatamaker;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.riversun.file_grabber.TextFileWriter;
import org.riversun.ml.fakedatamaker.AttributeNumeric.AttributeNumericValue;

/**
 * 
 * @author Tom Misawa (riversun.org@gmail.com)
 */
public class RegInstanceGenerator {

	private List<Attribute> attrs = new ArrayList<Attribute>();

	public RegInstanceGenerator attr(Attribute attr) {
		attrs.add(attr);
		return RegInstanceGenerator.this;
	}

	private String generateCsvRandomValues(double baseValue) {
		return generateCsvRandomValues(baseValue, 0.0);
	}

	private String toCsvLabels(String resultLabel) {
		StringBuilder sb = new StringBuilder();

		for (Attribute attr : attrs) {
			sb.append(attr.label);
			sb.append(",");
		}
		sb.append(resultLabel);

		return sb.toString();
	}

	public static interface DataRuleCompliantListener {
		public boolean isCompliant(AttributeCheck valueMap);
	}

	private DataRuleCompliantListener compliantListener = new DataRuleCompliantListener() {

		@Override
		public boolean isCompliant(AttributeCheck valueMap) {
			return true;
		}
	};

	public String generateCSV(int numOfLines) {
		return generateCSV(numOfLines, "target", 1.0);
	}

	public String generateCSV(int numOfLines, String targetLabel, double targetInitialValue) {
		return generateCSV(numOfLines, targetLabel, targetInitialValue, 0.0, true, true);
	}

	public String generateCSV(int numOfLines, String targetLabel, double targetInitialValue, double valueVolatility,
			boolean withHeader, boolean withId) {

		StringBuilder sb = new StringBuilder();
		if (withHeader) {
			if (withId) {
				sb.append("id,");
			}
			sb.append(toCsvLabels(targetLabel));
			sb.append("\n");
		}

		for (int i = 0; i < numOfLines; i++) {
			if (withId) {
				sb.append(i).append(",");
			}
			sb.append(generateCsvRandomValues(targetInitialValue, valueVolatility));
			sb.append("\n");
		}
		return sb.toString();

	}

	public void setDataRuleCompliantListener(DataRuleCompliantListener listener) {
		compliantListener = listener;
	}

	public String generateCsvRandomValues(double baseValue, double volatility) {

		Map<String, AttributeValue> attributeValues = new LinkedHashMap<>();
		final StringBuilder sb = new StringBuilder();

		double computedValue = 0;

		do {
			computedValue = baseValue + (volatility * Math.random() * baseValue);
			sb.setLength(0);
			for (Attribute attr : attrs) {
				if (attr.isNominal) {
					AttributeNominal randomNominal = attr.generateRandomNominal();
					sb.append(randomNominal.name);
					sb.append(",");
					computedValue *= randomNominal.coefficient;

					attributeValues.put(attr.label, randomNominal);
				} else {
					AttributeNumericValue randomNumeric = attr.generateRandomNumeric();
					sb.append((int) randomNumeric.numericValue);
					sb.append(",");
					computedValue *= randomNumeric.coefficient;
					attributeValues.put(attr.label, randomNumeric);
				}

			}
		} while (!compliantListener.isCompliant(new AttributeCheck(attributeValues)));

		sb.append((int) computedValue);
		return sb.toString();
	}

	public void save(File file, String string) {

		TextFileWriter w = new TextFileWriter();
		w.writeText(file, string, "UTF-8", false);
	}

}