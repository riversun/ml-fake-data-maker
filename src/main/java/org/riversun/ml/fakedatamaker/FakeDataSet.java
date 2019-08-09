
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
import java.util.List;

import org.riversun.file_grabber.TextFileWriter;

/**
 * Fake Data Generator for Machine Learning
 * 
 * @author Tom Misawa (riversun.org@gmail.com)
 *
 */
public class FakeDataSet {

    private List<Attribute> attr;
    private DataType type;
    private OutputFormat outputFormat;
    private DataRuleCompliantListener compliantListener;
    private Integer numOfLines;
    private String targetLabel;
    private double targetInitialValue;
    private double valueVolatility;
    private boolean withHeader;
    private boolean withId;
    private String nameOfData;
    private Long seed;

    public static class Builder {

        private List<Attribute> attr = new ArrayList<Attribute>();
        private DataType type = DataType.REGRESSION;
        private OutputFormat outputFormat = OutputFormat.CSV;
        private DataRuleCompliantListener compliantListener;
        private Integer numOfLines = 100;
        private String targetLabel = "label";
        private double targetInitialValue = 1.0;
        private double valueVolatility = 0.0;
        private boolean withHeader = true;
        private boolean withId = true;
        private String nameOfData = "dataset";
        private Long seed = null;

        public Builder() {

        }

        public Builder attr(List<Attribute> attr) {
            this.attr = attr;
            return Builder.this;
        }

        public Builder addAttr(Attribute attr) {
            this.attr.add(attr);
            return Builder.this;
        }

        public Builder nameOfData(String nameOfData) {
            this.nameOfData = nameOfData;
            return Builder.this;
        }

        public Builder type(DataType type) {
            this.type = type;
            return Builder.this;
        }

        public Builder seed(long seed) {
            this.seed = seed;
            return Builder.this;
        }

        public Builder outputFormat(OutputFormat outputFormat) {
            this.outputFormat = outputFormat;
            return Builder.this;
        }

        public Builder compliantListener(DataRuleCompliantListener compliantListener) {
            this.compliantListener = compliantListener;
            return Builder.this;
        }

        public Builder numOfLines(Integer numOfLines) {
            this.numOfLines = numOfLines;
            return Builder.this;
        }

        public Builder targetLabel(String targetLabel) {
            this.targetLabel = targetLabel;
            return Builder.this;
        }

        public Builder targetInitialValue(double targetInitialValue) {
            this.targetInitialValue = targetInitialValue;
            return Builder.this;
        }

        public Builder valueVolatility(double valueVolatility) {
            this.valueVolatility = valueVolatility;
            return Builder.this;
        }

        public Builder withHeader(boolean withHeader) {
            this.withHeader = withHeader;
            return Builder.this;
        }

        public Builder withId(boolean withId) {
            this.withId = withId;
            return Builder.this;
        }

        public FakeDataSet build() {
            if (this.attr == null) {
                throw new NullPointerException("The property \"attr\" is null. "
                        + "Please set the value by \"attr()\". "
                        + "The properties \"attr\", \"type\" and \"outputFormat\" are required.");
            }
            if (this.type == null) {
                throw new NullPointerException("The property \"type\" is null. "
                        + "Please set the value by \"type()\". "
                        + "The properties \"attr\", \"type\" and \"outputFormat\" are required.");
            }
            if (this.outputFormat == null) {
                throw new NullPointerException("The property \"outputFormat\" is null. "
                        + "Please set the value by \"outputFormat()\". "
                        + "The properties \"attr\", \"type\" and \"outputFormat\" are required.");
            }

            return new FakeDataSet(this);
        }
    }

    private FakeDataSet(Builder builder) {
        this.attr = builder.attr;
        this.type = builder.type;
        this.outputFormat = builder.outputFormat;
        this.compliantListener = builder.compliantListener;
        this.numOfLines = builder.numOfLines;
        this.targetLabel = builder.targetLabel;
        this.targetInitialValue = builder.targetInitialValue;
        this.valueVolatility = builder.valueVolatility;
        this.withHeader = builder.withHeader;
        this.withId = builder.withId;
        this.nameOfData = builder.nameOfData;
        this.seed = builder.seed;
    }

    public String get() {
        String result = "";
        if (this.seed != null) {
            MyMath.setSeed(this.seed);
        }
        if (this.type == DataType.REGRESSION) {

            switch (this.outputFormat) {
            case ARFF:
                RegressionDataSetGeneratorARFF regArff = new RegressionDataSetGeneratorARFF();
                regArff.setAttrs(this.attr);
                regArff.setNameOfData(this.nameOfData);

                if (this.compliantListener != null) {
                    regArff.setCompliantListener(this.compliantListener);
                }

                result = regArff.generateCSV(this.numOfLines, this.targetLabel, this.targetInitialValue, this.valueVolatility, this.withHeader, this.withId);

                break;
            case CSV:
                RegressionDataSetGeneratorCSV regCSV = new RegressionDataSetGeneratorCSV();
                regCSV.setAttrs(this.attr);

                if (this.compliantListener != null) {
                    regCSV.setCompliantListener(this.compliantListener);
                }

                result = regCSV.generateCSV(this.numOfLines, this.targetLabel, this.targetInitialValue, this.valueVolatility, this.withHeader, this.withId);

                break;
            }
        } else {
            // N/A
        }
        return result;
    }

    public void saveAsUTF8(File file) {
        save(file, "UTF-8");
    }

    public void save(File file, String encoding) {

        TextFileWriter w = new TextFileWriter();
        w.writeText(file, get(), encoding, false);
    }

    public void saveAsUTF8WithBom(File file, String encoding) {

        TextFileWriter w = new TextFileWriter();
        w.writeTextAsUTF8WithBOM(file, get(), false);
    }
}
