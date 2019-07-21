# Overview
Library to generate fake data for machine learningu (like prediction with linearregression,randomforest,gbt etc.)

It is licensed under [MIT](https://opensource.org/licenses/MIT).

# How To Use

**Example Code**

Generate fake data that can be used in linear regression (or something like that).

**Maven dependency**

```xml
<dependency>
<groupId>org.riversun</groupId>
<artifactId>fake-data-maker</artifactId>
<version>1.0.0</version>
</dependency>
```

**Example code**

```java

import java.io.File;
import org.riversun.ml.fakedatamaker.AttributeNumeric.ComputeMethod;
import org.riversun.ml.fakedatamaker.RegInstanceGenerator.DataRuleCompliantListener;

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

		RegInstanceGenerator instance = new RegInstanceGenerator()
				.attr(acGenryo)
				.attr(shape)
				.attr(weightg)
				.attr(brand)
				.attr(shop);

		// You can check the rules so that the data is not semantically inconsistent.
		instance.setDataRuleCompliantListener(new DataRuleCompliantListener() {
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
		});

		String generatedCSV = instance.generateCSV(500, "price", basePrice);
		System.out.println(generatedCSV);
		instance.save(new File("c:/temp/price.txt"), generatedCSV);

	}
}
```

**Result**
You can get like this CSV file.

In the case of this data,
it is possible to predict the price of the gem (objective variable ) from the attribute (explanatory variable) using linear regression etc.


```shell
id,material,shape,weight,brand,shop,price
0,Gold,Brace,42,NationalBrand,DepartmentStore,561
1,Gold,Earrings,51,NoBrand,DepartmentStore,269
2,Silver,Ring,49,WorldTopBrand,DepartmentStore,672
3,Gold,Earrings,43,WorldTopBrand,BrandStore,2337
4,Platinum,Ring,10,FamouseBrand,BrandStore,1279
5,Diamond,Neckless,42,NoBrand,DiscountStore,383
6,Gold,Earrings,13,WorldTopBrand,BrandStore,1603
7,Gold,Brace,59,FamouseBrand,BrandStore,1558
8,Platinum,Earrings,47,WorldTopBrand,DepartmentStore,3173
9,Silver,Ring,38,NationalBrand,DepartmentStore,156
10,Diamond,Neckless,25,FamouseBrand,BrandStore,2299
11,Platinum,Ring,34,FamouseBrand,BrandStore,1940
12,Gold,Brooch,21,NoBrand,DiscountStore,154
13,Gold,Earrings,18,WorldTopBrand,DepartmentStore,1607
14,Gold,Earrings,35,NoBrand,DiscountStore,178
15,Platinum,Ring,37,NationalBrand,BrandStore,881
16,Silver,Brooch,39,NoBrand,DiscountStore,55
17,Gold,Earrings,43,NationalBrand,DepartmentStore,514
18,Silver,Brace,35,FamouseBrand,BrandStore,409
19,Platinum,Brooch,13,NationalBrand,DiscountStore,393
20,Gold,Earrings,53,NationalBrand,DiscountStore,400
```


<table>
<tr><td>id</td><td>material</td><td>shape</td><td>weight</td><td>brand</td><td>shop</td><td>price</td></tr>
<tr><td x:num="">0</td><td>Diamond</td><td>Neckless</td><td x:num="">20</td><td>FamouseBrand</td><td>BrandStore</td><td x:num="">2144</td></tr>
<tr><td x:num="">1</td><td>Diamond</td><td>Brooch</td><td x:num="">17</td><td>NoBrand</td><td>DepartmentStore</td><td x:num="">393</td></tr>
<tr><td x:num="">2</td><td>Silver</td><td>Ring</td><td x:num="">55</td><td>WorldTopBrand</td><td>BrandStore</td><td x:num="">781</td></tr>
<tr><td x:num="">3</td><td>Gold</td><td>Brooch</td><td x:num="">46</td><td>NoBrand</td><td>DepartmentStore</td><td x:num="">262</td></tr>
<tr><td x:num="">4</td><td>Diamond</td><td>Brooch</td><td x:num="">50</td><td>NationalBrand</td><td>DepartmentStore</td><td x:num="">1070</td></tr>
<tr><td x:num="">5</td><td>Silver</td><td>Ring</td><td x:num="">12</td><td>NoBrand</td><td>DepartmentStore</td><td x:num="">53</td></tr>
<tr><td x:num="">6</td><td>Diamond</td><td>Brooch</td><td x:num="">19</td><td>NoBrand</td><td>DiscountStore</td><td x:num="">296</td></tr>
<tr><td x:num="">7</td><td>Diamond</td><td>Earrings</td><td x:num="">43</td><td>NationalBrand</td><td>DepartmentStore</td><td x:num="">1034</td></tr>
<tr><td x:num="">8</td><td>Platinum</td><td>Brace</td><td x:num="">56</td><td>NoBrand</td><td>DepartmentStore</td><td x:num="">454</td></tr>
<tr><td x:num="">9</td><td>Platinum</td><td>Brooch</td><td x:num="">39</td><td>WorldTopBrand</td><td>DepartmentStore</td><td x:num="">3007</td></tr>
<tr><td x:num="">10</td><td>Silver</td><td>Ring</td><td x:num="">48</td><td>NoBrand</td><td>DepartmentStore</td><td x:num="">83</td></tr>
<tr><td x:num="">11</td><td>Platinum</td><td>Earrings</td><td x:num="">43</td><td>WorldTopBrand</td><td>BrandStore</td><td x:num="">3511</td></tr>
<tr><td x:num="">12</td><td>Silver</td><td>Brooch</td><td x:num="">37</td><td>NationalBrand</td><td>DiscountStore</td><td x:num="">109</td></tr>
<tr><td x:num="">13</td><td>Gold</td><td>Brooch</td><td x:num="">33</td><td>WorldTopBrand</td><td>DepartmentStore</td><td x:num="">1921</td></tr>
<tr><td x:num="">14</td><td>Platinum</td><td>Neckless</td><td x:num="">25</td><td>NoBrand</td><td>DiscountStore</td><td x:num="">248</td></tr>
<tr><td x:num="">15</td><td>Platinum</td><td>Earrings</td><td x:num="">28</td><td>NationalBrand</td><td>DepartmentStore</td><td x:num="">689</td></tr>
<tr><td x:num="">16</td><td>Silver</td><td>Earrings</td><td x:num="">42</td><td>NationalBrand</td><td>BrandStore</td><td x:num="">174</td></tr>
<tr><td x:num="">17</td><td>Gold</td><td>Ring</td><td x:num="">40</td><td>NationalBrand</td><td>DepartmentStore</td><td x:num="">529</td></tr>
<tr><td x:num="">18</td><td>Silver</td><td>Ring</td><td x:num="">14</td><td>WorldTopBrand</td><td>BrandStore</td><td x:num="">522</td></tr>
<tr><td x:num="">19</td><td>Silver</td><td>Brooch</td><td x:num="">18</td><td>FamouseBrand</td><td>BrandStore</td><td x:num="">307</td></tr>
</table>

# **Download example data for learning and regression**
Data file is also MIT licensed.

# **Gem Prices**

**Purpose**
Predict sales price from gem attributes

**Format**

```
id,material,shape,weight,brand,shop,price
0,Gold,Brace,42,NationalBrand,DepartmentStore,561
1,Gold,Earrings,51,NoBrand,DepartmentStore,269
...
```

**Data File**

- [**CSV File(EN)**](https://raw.githubusercontent.com/riversun/ml-fake-data-maker/master/datasets/gem_price.csv)
- [**CSV File(JA) UTF-8 with BOM**](https://raw.githubusercontent.com/riversun/ml-fake-data-maker/master/datasets/gem_price_ja.csv)
