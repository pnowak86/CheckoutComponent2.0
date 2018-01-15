package priceCalculator;
import dto.Item;
import org.junit.Before;
import org.junit.Test;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class DiscountCalculatorTest {

    private class CustomDiscountCalculator extends DiscountCalculator {
        @Override
        public List<Item> getStockItemsForCalculator(){
          return testList;
        }

    }

    List<Item> testList = new ArrayList<>();

    CustomDiscountCalculator discountCalculator;

    @Before
    public void setupTests() {

        discountCalculator = new CustomDiscountCalculator();

        testList.add(new Item(1, "LEDTv", new BigInteger("40"),
                new Integer("3"), new BigInteger("70")));
        testList.add(new Item(2, "Mp3Player", new BigInteger("10"),
                new Integer("2"), new BigInteger("15")));
        testList.add(new Item(1, "DvdPlayer", new BigInteger("30"),
                new Integer("4"), new BigInteger("60")));
        testList.add(new Item(2, "MobilePhone", new BigInteger("25"),
                new Integer("2"), new BigInteger("40")));

    }

    @Test
    public void shouldCalculatePriceForSingleLEDTv() throws Exception {
        assertEquals(new BigInteger("40"), discountCalculator.discountForUnits("LEDTv", 1));
    }

    @Test
    public void shouldCalculateDiscountPriceForLEDTv() throws Exception {
        assertEquals(new BigInteger("70"), discountCalculator.discountForUnits("LEDTv", 3));
    }

    @Test
    public void shouldCalculateTotalPriceForCoupleLEDTv() throws Exception {
        assertEquals(new BigInteger("320"), discountCalculator.discountForUnits("LEDTv", 13));
    }

    @Test
    public void shouldCalculatePriceForSingleMp3Player() throws Exception {
        assertEquals(new BigInteger("10"), discountCalculator.discountForUnits("mp3player", 1));
    }

    @Test
    public void shouldCalculateDiscountPriceForMp3Player() throws Exception {
        assertEquals(new BigInteger("15"), discountCalculator.discountForUnits("MP3PLAYER", 2));
    }

    @Test
    public void shouldCalculateTotalPriceForCoupleMp3Player() throws Exception {
        assertEquals(new BigInteger("115"), discountCalculator.discountForUnits("mp3PLAYER", 15));
    }

    @Test
    public void shouldCalculatePriceForSingleDvdPlayer() throws Exception {
        assertEquals(new BigInteger("30"), discountCalculator.discountForUnits("DVDPLAYER", 1));
    }

    @Test
    public void shouldCalculateDiscountPriceForDvdPlayer() throws Exception {
        assertEquals(new BigInteger("60"), discountCalculator.discountForUnits("DvdPlayer", 4));
    }

    @Test
    public void shouldCalculateTotalPriceForCoupleDvdPlayer() throws Exception {
        assertEquals(new BigInteger("150"), discountCalculator.discountForUnits("dvdplayer", 7));
    }

    @Test
    public void shouldCalculatePriceForSingleMobilePhone() throws Exception {
        assertEquals(new BigInteger("25"), discountCalculator.discountForUnits("MoBiLePHONE", 1));
    }

    @Test
    public void shouldCalculateDiscountPriceForMobilePhone() throws Exception {
        assertEquals(new BigInteger("40"), discountCalculator.discountForUnits("MobilePhone", 2));
    }

    @Test
    public void shouldCalculateTotalPriceForCoupleMobilePhone() throws Exception {
        assertEquals(new BigInteger("345"), discountCalculator.discountForUnits("mobilephone", 17));
    }

}