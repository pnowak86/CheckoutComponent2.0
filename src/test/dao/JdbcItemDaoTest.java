package dao;

import dto.BasketItem;
import org.junit.Before;

import java.util.ArrayList;

import dto.Item;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import priceCalculator.DiscountCalculator;
import priceCalculator.PriceCalculatorInterface;

import java.math.BigInteger;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class JdbcItemDaoTest {

    List<Item> testListStock = new ArrayList<>();
    List<BasketItem> testListBasket = new ArrayList<>();
    JdbcItemDao jdbcItemDao;


    @Before
    public void setup() {
        jdbcItemDao = new CustomJdbcItemDao();


        testListStock.add(new Item(1, "LEDTv", new BigInteger("40"),
                new Integer("3"), new BigInteger("70")));
        testListStock.add(new Item(2, "Mp3Player", new BigInteger("10"),
                new Integer("2"), new BigInteger("15")));
        testListStock.add(new Item(1, "DvdPlayer", new BigInteger("30"),
                new Integer("4"), new BigInteger("60")));
        testListStock.add(new Item(2, "MobilePhone", new BigInteger("25"),
                new Integer("2"), new BigInteger("40")));



        testListBasket.add(new BasketItem(1,"LEDTv",
                new Integer(3), new BigInteger("88")));
        testListBasket.add(new BasketItem(2,"Mp3Player",
                new Integer(20), new BigInteger("188")));
        testListBasket.add(new BasketItem(3,"DvdPlayer",
                new Integer(17), new BigInteger("887")));
        testListBasket.add(new BasketItem(4,"MobilePhone",
                new Integer(31), new BigInteger("77")));





    }


    @Test
    public void shouldReturnTrueIfTvIsInStock() throws Exception {
        assertTrue(jdbcItemDao.checkStockItems("LEDTv"));

    }

    @Test
    public void shouldReturnTrueIfMp3IsInStock() throws Exception {
        assertTrue(jdbcItemDao.checkStockItems("mp3player"));

    }

    @Test
    public void shouldReturnTrueIfMobilePhoneIsInStock() throws Exception {
        assertTrue(jdbcItemDao.checkStockItems("MOBILEPHONE"));

    }
    @Test
    public void shouldReturnTrueIfDvdPlayerIsInStock() throws Exception {
        assertTrue(jdbcItemDao.checkStockItems("DvDpLaYer"));

    }

    @Test
    public void shouldReturnLedTveBasketId() throws Exception {
        assertEquals(1,jdbcItemDao.checkBasketItems("ledtv"));

    }
    @Test
    public void shouldReturnMp3PlayerBasketId() throws Exception {
        assertEquals(2,jdbcItemDao.checkBasketItems("MP3PLAYER"));

    }
    @Test
    public void shouldReturnDvdPlayerBasketId() throws Exception {
        assertEquals(3,jdbcItemDao.checkBasketItems("DvDpLaYer"));

    }

    @Test
    public void shouldReturnMobilePhoneBasketId() throws Exception {
        assertEquals(4,jdbcItemDao.checkBasketItems("MOBILEPHONE"));

    }

    @Test
    public void checkBasketItemsByWrongName() throws Exception {
        assertEquals(-1,jdbcItemDao.checkBasketItems("car"));

    }




//    @Test
//    public void checkQuantityInBasketTest() throws Exception {
//       assertEquals(3,jdbcItemDao.checkQuantityInBasket("LEDTv"));
//
//    }




    private class CustomJdbcItemDao extends JdbcItemDao {
        @Override
        public List<Item> getAllStockItems() {
            return testListStock;
        }

        @Override
        public List<BasketItem> getAllBasketItems() {
            return testListBasket;
        }

    }

}