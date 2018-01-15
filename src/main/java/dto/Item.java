package dto;


import java.math.BigInteger;

public class Item {
    private int stockId;
    private String itemName;
    private BigInteger stockPrice;
    private Integer unitsForDiscount;
    private BigInteger discountPrice;

    public Item() {
    }

    public Item(int stockId, String item_name, BigInteger stockPrice,
                Integer unitsForDiscount, BigInteger discountPrice) {
        this.stockId = stockId;
        this.itemName = item_name;
        this.stockPrice = stockPrice;
        this.unitsForDiscount = unitsForDiscount;
        this.discountPrice = discountPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigInteger getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigInteger stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Integer getUnitsForDiscount() {
        return unitsForDiscount;
    }

    public void setUnitsForDiscount(Integer unitsForDiscount) {
        this.unitsForDiscount = unitsForDiscount;
    }

    public BigInteger getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigInteger discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }


}