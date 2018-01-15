package dto;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class ItemTest {


    Item item;

    @Before
    public void setup() {
        this.item = new Item(1,"LEDTv", new BigInteger("21"), new Integer("2"), new BigInteger("70"));
    }


    @Test
    public void shouldGetName() throws Exception {
        assertEquals("LEDTv", item.getItemName());
    }

    @Test
    public void shouldSetName() throws Exception {
        item.setItemName("Mp3Player");
        assertEquals("Mp3Player", item.getItemName());
    }

    @Test
    public void shouldGetPrice() throws Exception {
        assertEquals(new BigInteger("21"), item.getStockPrice());
    }

    @Test
    public void shouldSetPrice() throws Exception {
        item.setStockPrice(new BigInteger("12"));
        assertEquals(new BigInteger("12"), item.getStockPrice());
    }

    @Test
    public void shouldGetDiscountQuantity() throws Exception {
        assertEquals(new Integer("2"), item.getUnitsForDiscount());
    }

    @Test
    public void shouldSetDiscountQuantity() throws Exception {
        item.setUnitsForDiscount(new Integer("11"));
        assertEquals(new Integer("11"), item.getUnitsForDiscount());
    }

    @Test
    public void shouldGetSpecialPrice() throws Exception {
        assertEquals(new BigInteger("70"), item.getDiscountPrice());
    }

    @Test
    public void shouldSetSpecialPrice() throws Exception {
        item.setDiscountPrice(new BigInteger("190"));
        assertEquals(new BigInteger("190"), item.getDiscountPrice());
    }


    @Test
    public void shouldGetStockId() throws Exception {
        assertEquals(1, item.getStockId());

    }

    @Test
    public void shouldSetStockId() throws Exception {
        item.setStockId(20);
        assertEquals(20, item.getStockId());
    }

}