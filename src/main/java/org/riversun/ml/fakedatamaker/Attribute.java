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

import org.riversun.ml.fakedatamaker.AttributeNumeric.AttributeNumericValue;

/**
 * 
 * @author Tom Misawa (riversun.org@gmail.com)
 */
public class Attribute {

	public String label;
	public boolean isNominal = false;
	public AttributeNominal[] nominals;
	public AttributeNumeric numeric;

	/**
	 * Initialize by nominal attribute
	 * 
	 * @param label
	 * @param nominals
	 */
	public Attribute(String label, AttributeNominal... nominals) {
		this.label = label;
		this.nominals = nominals;
		for (int i = 0; i < nominals.length; i++) {
			nominals[i].index = i;
		}
		this.isNominal = true;
	}

	/**
	 * Initialize by numeric attribute
	 * 
	 * @param label
	 * @param numeric
	 */
	public Attribute(String label, AttributeNumeric numeric) {
		this.label = label;
		this.numeric = numeric;
	}

	/**
	 * Returns randomly selected nominal
	 * 
	 * @return
	 */
	AttributeNominal generateRandomNominal() {
		int idx = (int) (Math.random() * nominals.length);
		return nominals[idx];
	}

	/**
	 * Returns a random value that fits within the specified range
	 * 
	 * @return
	 */
	AttributeNumericValue generateRandomNumeric() {
		return numeric.getRandomNumeric();
	}

}