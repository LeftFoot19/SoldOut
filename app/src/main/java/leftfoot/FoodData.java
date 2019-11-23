package leftfoot;

import java.io.Serializable;
import java.util.Date;

public class FoodData implements Serializable {

    //サーバ側IDと合わせる
    private static final long serialVersionUID = 6418725139354087069L;

    public int productid;
    public int iniPrice;
    public int discountPrice1;
    public int discountPrice2;
    public String productName;
    public Date bestBeforeDate;

    @Override
    public String toString() {
        String string = "ID: " + this.productid +
                "\nProductName: " + this.productName +
                "\nInitialPrice: " + this.iniPrice +
                "\nDiscountPrice1:" + this.discountPrice1 +
                "\nDiscountPrice2:" + this.discountPrice2 +
                "\nBestBoforeDate: " + this.bestBeforeDate.toString();
        return string;
    }

}
