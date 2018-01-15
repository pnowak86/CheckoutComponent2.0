package dto;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class BasketItemTest {
    BasketItem basketItem;
    Item item;

    @Before
    public void setup() {
        this.basketItem = new BasketItem(1, "LEDTv", 3, new BigInteger("70"));
    }

    @Test
    public void shoulGetBasketId() throws Exception {
        assertEquals(1, basketItem.getBasketId());
    }

    @Test
    public void shouldSetBasketId() throws Exception {
        basketItem.setBasketId(2);
        assertEquals(2, basketItem.getBasketId());
    }

    @Test
    public void shouldGetBasketItemName() throws Exception {
        assertEquals("LEDTv", basketItem.getBasketItemName());
    }

    @Test
    public void shouldSetBasketItemName() throws Exception {
        basketItem.setBasketItemName("DvdPlayer");
        assertEquals("DvdPlayer", basketItem.getBasketItemName());
    }

    @Test
    public void shouldGetInBasketQuantity() throws Exception {
        assertEquals((Integer) 3, basketItem.getInBasketQuantity());
    }

    @Test
    public void shouldSetInBasketQuantity() throws Exception {
        basketItem.setInBasketQuantity(77);
        assertEquals((Integer) 77, basketItem.getInBasketQuantity());
    }

    @Test
    public void shouldGetBasketPriceWithDiscount() throws Exception {
        assertEquals(new BigInteger("70"), basketItem.getBasketPriceWithDiscount());
    }

    @Test
    public void shouldSetBasketPriceWithDiscount() throws Exception {
        basketItem.setBasketPriceWithDiscount(new BigInteger("170"));
        assertEquals(new BigInteger("170"), basketItem.getBasketPriceWithDiscount());
    }

}