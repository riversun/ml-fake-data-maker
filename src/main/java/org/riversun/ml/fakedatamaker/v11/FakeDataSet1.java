package org.riversun.ml.fakedatamaker.v11;

import java.util.ArrayList;
import java.util.List;

public class FakeDataSet1 {

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
	private String result;

	public static class Builder {

		private List<Attribute> attr = new ArrayList<Attribute>();
		private DataType type = DataType.REGRESSION;
		private OutputFormat outputFormat = OutputFormat.CSV;
		private DataRuleCompliantListener compliantListener;
		private Integer numOfLines = 100;
		private String targetLabel = "label";
		private double targetInitialValue = 0;
		private double valueVolatility = 0.0;
		private boolean withHeader = true;
		private boolean withId = true;

		private String result = null;

		public Builder() {

		}

		Builder(List<Attribute> attr, DataType type, OutputFormat outputFormat, DataRuleCompliantListener compliantListener, Integer numOfLines, String targetLabel, double targetInitialValue,
				double valueVolatility, boolean withHeader, boolean withId, String result) {
			this.attr = attr;
			this.type = type;
			this.outputFormat = outputFormat;
			this.compliantListener = compliantListener;
			this.numOfLines = numOfLines;
			this.targetLabel = targetLabel;
			this.targetInitialValue = targetInitialValue;
			this.valueVolatility = valueVolatility;
			this.withHeader = withHeader;
			this.withId = withId;
			this.result = result;
		}

		public Builder attr(List<Attribute> attr) {
			this.attr = attr;
			return Builder.this;
		}

		public Builder addAttr(Attribute attr) {
			this.attr.add(attr);
			return Builder.this;
		}

		public Builder type(DataType type) {
			this.type = type;
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

		public FakeDataSet1 build() {
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
			switch (this.outputFormat) {
			case ARFF:
				break;
			case CSV:

				RegressionDataSetGenerator r = new RegressionDataSetGenerator();
				r.setAttrs(this.attr);
				if (this.compliantListener != null) {
					r.setCompliantListener(this.compliantListener);
				}
				this.result = r.generateCSV(this.numOfLines, this.targetLabel, this.targetInitialValue, this.valueVolatility, this.withHeader, this.withId);

				break;
			}

			return new FakeDataSet1(this);
		}
	}

	private FakeDataSet1(Builder builder) {
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
		this.result=builder.result;
	}

	public String get() {
		return result;
	}
}
