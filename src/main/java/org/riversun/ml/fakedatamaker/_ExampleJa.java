package org.riversun.ml.fakedatamaker;

import java.io.File;

import org.riversun.ml.fakedatamaker.AttributeNumeric.ComputeMethod;
import org.riversun.ml.fakedatamaker.RegInstanceGenerator.DataRuleCompliantListener;

class _ExampleJa {

	public static void main(String[] args) {

		// ベースの価格を決める
		double basePrice = 1000;

		// 各属性を作る
		Attribute acGenryo = new Attribute(
				"原料",
				new AttributeNominal("ダイア", 20),
				new AttributeNominal("プラチナ", 15),
				new AttributeNominal("ゴールド", 10),
				new AttributeNominal("シルバー", 3));

		Attribute brand = new Attribute(
				"ブランド",
				new AttributeNominal("海外超有名ブランド", 8.0),
				new AttributeNominal("海外有名ブランド", 4.5),
				new AttributeNominal("国内有名ブランド", 2.0),
				new AttributeNominal("ノーブランド", 1.0));

		Attribute shop = new Attribute(
				"販売店",
				new AttributeNominal("直営店", 1.7),
				new AttributeNominal("百貨店", 1.5),
				new AttributeNominal("量販店", 1.2),
				new AttributeNominal("激安ショップ", 1.1));

		Attribute shape = new Attribute(
				"加工",
				new AttributeNominal("指輪", 1.10),
				new AttributeNominal("ネックレス", 1.07),
				new AttributeNominal("イアリング", 1.05),
				new AttributeNominal("ブローチ", 1.05),
				new AttributeNominal("ブレスレット", 1.15));

		Attribute weightg = new Attribute("重さ",
				new AttributeNumeric(10, 60, ComputeMethod.LOG10, 1));

		RegInstanceGenerator instance = new RegInstanceGenerator()
				.attr(acGenryo)
				.attr(shape)
				.attr(weightg)
				.attr(brand)
				.attr(shop);

		// データが意味的に不整合を起こさないようにルールを決める
		instance.setDataRuleCompliantListener(new DataRuleCompliantListener() {
			@Override
			public boolean isCompliant(AttributeCheck check) {

				if (check.nominalEquals("ブランド", "ノーブランド") && check.nominalEquals("販売店", "直営店")) {
					// ・ノーブランドには「直営店」は無い
					return false;
				}
				if (check.nominalEquals("ブランド", "海外超有名ブランド") &&
						(check.nominalEquals("販売店", "激安ショップ")) || check.nominalEquals("販売店", "量販店")) {
					// ・海外超有名ブランドは「激安ショップ」や「量販店」では取り扱いがない
					return false;
				}
				if (check.nominalEquals("ブランド", "海外有名ブランド") &&
						(check.nominalEquals("販売店", "激安ショップ"))) {
					// ・海外有名ブランドは「激安ショップ」では取り扱いがない
					return false;
				}

				return true;
			}
		});

		String generatedCSV = instance.generateCSV(500, "価格", basePrice);
		System.out.println(generatedCSV);
		instance.save(new File("c:/temp/price.txt"), generatedCSV);

	}
}
