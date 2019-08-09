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

/**
 * 
 * @author Tom Misawa (riversun.org@gmail.com)
 */
public class AttributeNumeric {

	public double minValue;
	public double maxValue;
	public double coefficient;
	public ComputeMethod computeMethod;

	public AttributeNumeric(double minValue, double maxValue, ComputeMethod computeMethod, double coefficient) {

		this.minValue = minValue;
		this.maxValue = maxValue;
		this.coefficient = coefficient;
		this.computeMethod = computeMethod;
	}

	public AttributeNumericValue getRandomNumeric() {
		double generatedNumericValue = (MyMath.random() * (maxValue - minValue) + minValue);

		double _coefficient = 1;

		switch (computeMethod) {
		case NORMAL:
			_coefficient = (generatedNumericValue) * coefficient;
			break;
		case SQRT:
			_coefficient = Math.sqrt(generatedNumericValue) * coefficient;
			break;
		case LOG10:
			_coefficient = Math.log10(generatedNumericValue) * coefficient;
			break;
		}

		AttributeNumericValue _numericValue = new AttributeNumericValue(generatedNumericValue, _coefficient);
		return _numericValue;

	}

	public static class AttributeNumericValue implements AttributeValue {
		public double numericValue;
		public double coefficient;

		public AttributeNumericValue(double numericValue, double coefficient) {

			this.numericValue = numericValue;
			this.coefficient = coefficient;
		}

	}

}