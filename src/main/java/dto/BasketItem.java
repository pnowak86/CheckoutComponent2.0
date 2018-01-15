package dto;

import java.math.BigInteger;

public class BasketItem {
    private int basketId;
    private String basketItemName;
    private Integer inBasketQuantity;
    private BigInteger basketPriceWithDiscount;

    public BasketItem() {
    }

    public BasketItem(int basketId, String basketItemName, Integer inBasketQuantity, BigInteger basketPriceWithDiscount) {
        this.basketId = basketId;
        this.basketItemName = basketItemName;
        this.inBasketQuantity = inBasketQuantity;
        this.basketPriceWithDiscount = basketPriceWithDiscount;
    }


    public int getBasketId() {
        return basketId;
    }

    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }

    public String getBasketItemName() {
        return basketItemName;
    }

    public void setBasketItemName(String basketItemName) {
        this.basketItemName = basketItemName;
    }

    public Integer getInBasketQuantity() {
        return inBasketQuantity;
    }

    public void setInBasketQuantity(Integer inBasketQuantity) {
        this.inBasketQuantity = inBasketQuantity;
    }

    public BigInteger getBasketPriceWithDiscount() {
        return basketPriceWithDiscount;
    }

    public void setBasketPriceWithDiscount(BigInteger basketPriceWithDiscount) {
        this.basketPriceWithDiscount = basketPriceWithDiscount;
    }


}
