package org.riversun.ml.fakedatamaker;

import java.io.File;

/**
 * 
 * 回帰分析に使用するためのフェイクデータを生成する
 *
 */
class _ExampleJa {

    public static void main(String[] args) {

        // ベースの価格を決める
        double basePrice = 1000;

        // 各属性を作る
        Attribute material = new Attribute(
                // 属性名（カテゴリ変数名）
                "material",
                // new AttributeNominal(属性値,目的変数に与える重み),
                new AttributeNominal("ダイア", 20),
                new AttributeNominal("プラチナ", 15),
                new AttributeNominal("ゴールド", 10),
                new AttributeNominal("シルバー", 3));

        Attribute brand = new Attribute(
                "brand",
                new AttributeNominal("海外超有名ブランド", 8.0),
                new AttributeNominal("海外有名ブランド", 4.5),
                new AttributeNominal("国内有名ブランド", 2.0),
                new AttributeNominal("ノーブランド", 1.0));

        Attribute shop = new Attribute(
                "shop",
                new AttributeNominal("直営店", 1.7),
                new AttributeNominal("百貨店", 1.5),
                new AttributeNominal("量販店", 1.2),
                new AttributeNominal("激安ショップ", 1.1));

        Attribute shape = new Attribute(
                "shape",
                new AttributeNominal("指輪", 1.10),
                new AttributeNominal("ネックレス", 1.07),
                new AttributeNominal("イアリング", 1.05),
                new AttributeNominal("ブローチ", 1.05),
                new AttributeNominal("ブレスレット", 1.15));

        Attribute weightg = new Attribute("weight",
                new AttributeNumeric(10, 60, ComputeMethod.LOG10, 1));

        FakeDataSet ds = new FakeDataSet.Builder()
                .type(DataType.REGRESSION)
                .outputFormat(OutputFormat.CSV)// CSV or ARFF
                .nameOfData("gemsales")
                .addAttr(material)
                .addAttr(shape)
                .addAttr(weightg)
                .addAttr(brand)
                .addAttr(shop)
                .compliantListener( // データが意味的に不整合を起こさないようにルールを決める
                        new DataRuleCompliantListener() {
                            @Override
                            public boolean isCompliant(AttributeCheck check) {

                                if (check.nominalEquals("brand", "ノーブランド") && check.nominalEquals("shop", "直営店")) {
                                    // ・ノーブランドには「直営店」は無い
                                    return false;
                                }
                                if (check.nominalEquals("brand", "海外超有名ブランド") &&
                                        (check.nominalEquals("shop", "激安ショップ")) || check.nominalEquals("shop", "量販店")) {
                                    // ・海外超有名ブランドは「激安ショップ」や「量販店」では取り扱いがない
                                    return false;
                                }
                                if (check.nominalEquals("brand", "海外有名ブランド") &&
                                        (check.nominalEquals("shop", "激安ショップ"))) {
                                    // ・海外有名ブランドは「激安ショップ」では取り扱いがない
                                    return false;
                                }

                                return true;
                            }
                        })
                .numOfLines(500)// データ数
                .targetLabel("price")// ターゲットラベル（予測に使用）
                .targetInitialValue(basePrice)
                .valueVolatility(0.0)
                //.seed(1)// 乱数のシード(1以上のlongで指定)
                .withHeader(true)
                .withId(true)
                .build();

        //ds.save(new File("c:/temp/gem_price_ja.csv"), "UTF-8");
        System.out.println(ds.get());

    }
}
