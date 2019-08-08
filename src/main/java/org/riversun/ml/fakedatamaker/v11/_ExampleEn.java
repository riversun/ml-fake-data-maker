package org.riversun.ml.fakedatamaker.v11;

import java.io.File;

/**
 * 
 * Generate fake data that can be used in linear regression analysis
 *
 */
class _ExampleEn {

	public static void main(String[] args) {

		// Set base price
		double basePrice = 10;

		// Create attributes
		Attribute acGenryo = new Attribute(
				"material",
				new AttributeNominal("Diamond", 20),
				new AttributeNominal("Platinum", 15),
				new AttributeNominal("Gold", 10),
				new AttributeNominal("Silver", 3));

		Attribute brand = new Attribute(
				"brand",
				new AttributeNominal("WorldTopBrand", 8.0),
				new AttributeNominal("FamouseBrand", 4.5),
				new AttributeNominal("NationalBrand", 2.0),
				new AttributeNominal("NoBrand", 1.0));

		Attribute shop = new Attribute(
				"shop",
				new AttributeNominal("BrandStore", 1.7),
				new AttributeNominal("DepartmentStore", 1.5),
				new AttributeNominal("MassRetailer", 1.2),
				new AttributeNominal("DiscountStore", 1.1));

		Attribute shape = new Attribute(
				"shape",
				new AttributeNominal("Ring", 1.10),
				new AttributeNominal("Neckless", 1.07),
				new AttributeNominal("Earrings", 1.05),
				new AttributeNominal("Brooch", 1.05),
				new AttributeNominal("Brace", 1.15));

		Attribute weightg = new Attribute("weight",
				new AttributeNumeric(10, 60, ComputeMethod.LOG10, 1));

		FakeDataSet1 ds = new FakeDataSet1.Builder()
			.type(DataType.REGRESSION)
			.outputFormat(OutputFormat.CSV)
				.addAttr(acGenryo)
				.addAttr(shape)
				.addAttr(weightg)
				.addAttr(brand)
				.addAttr(shop)
				.compliantListener(new DataRuleCompliantListener() {
			@Override
			public boolean isCompliant(AttributeCheck check) {

				if (check.nominalEquals("brand", "NoBrand") && check.nominalEquals("shop", "BrandStore")) {
					// No-brand has no "BrandStore"
					return false;
				}
				if (check.nominalEquals("brand", "WorldTopBrand") &&
						(check.nominalEquals("shop", "DiscountStore")) || check.nominalEquals("shop", "MassRetailer")) {
					// WorldTopBrands are not handled at "DiscountStores" or "mass retailers"
					return false;
				}
				if (check.nominalEquals("brand", "FamouseBrand") &&
						(check.nominalEquals("shop", "DiscountStore"))) {
					// FamouseBrands are not handled at "DiscountStore"
					return false;
				}

				return true;
			}
		})
				.numOfLines(20)
				.targetLabel("price")
				.targetInitialValue(basePrice)
				.valueVolatility(0.0)
				.withHeader(true)
				.withId(true)
				.build();
				
//				 double valueVolatility,
//					boolean withHeader, boolean withId
//				
				

					
//		int numOfLines=20;
//		String generatedCSV = instance.generateCSV(numOfLines, "price", basePrice);
		System.out.println(ds.get());
		//instance.saveAsUTF8(new File("c:/temp/price.csv"), generatedCSV);

	}
}
