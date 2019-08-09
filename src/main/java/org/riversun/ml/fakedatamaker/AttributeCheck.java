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

import java.util.Map;

import org.riversun.ml.fakedatamaker.AttributeNumeric.AttributeNumericValue;

/**
 * 
 * @author Tom Misawa (riversun.org@gmail.com)
 */
public class AttributeCheck {

    private Map<String, AttributeValue> valueMap;

    public AttributeCheck(Map<String, AttributeValue> valueMap) {
        this.valueMap = valueMap;
    }

    public boolean nominalEquals(String label, String nominalValue) {

        AttributeNominal nominal = (AttributeNominal) valueMap.get(label);
        if (nominal.name.equals(nominalValue)) {
            return true;
        }
        return false;
    }

    public boolean numericEquals(String label, double numericValue) {

        AttributeNumericValue numeric = (AttributeNumericValue) valueMap.get(label);
        if (numeric.numericValue == numericValue) {
            return true;
        }

        return false;
    }
}
