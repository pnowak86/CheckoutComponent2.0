package priceCalculator;

import java.math.BigInteger;

public interface PriceCalculatorInterface {

    BigInteger discountForUnits(String itemName, int quantity);

}
