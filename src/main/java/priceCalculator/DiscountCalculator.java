package priceCalculator;

import config.DatabaseConnector;
import dao.JdbcItemDao;
import dto.Item;
import mapper.BasketItemRowMapper;
import mapper.ItemRowMapper;

import java.math.BigInteger;
import java.util.List;

public class DiscountCalculator implements PriceCalculatorInterface {

    List<Item> tempStockList = getStockItemsForCalculator();

    public List<Item> getStockItemsForCalculator() {
        ItemRowMapper itemRowMapper = new ItemRowMapper();
        BasketItemRowMapper basketItemRowMapper = new BasketItemRowMapper();
        JdbcItemDao jdbcItemDao = new JdbcItemDao(DatabaseConnector.getMySQLDataSource(), itemRowMapper, basketItemRowMapper);

        return jdbcItemDao.getAllStockItems();
    }

    @Override
    public BigInteger discountForUnits(String itemName, int quantity) {
        BigInteger priceWithDiscount = new BigInteger("0");
        Item tempItem = null;


        for (Item item : tempStockList) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                tempItem = item;
            }
        }
        int discountedGroups = quantity / tempItem.getUnitsForDiscount();
        int restWithoutDiscount = quantity % tempItem.getUnitsForDiscount();

        BigInteger discountedGroupsPrice =
                new BigInteger(String.valueOf(tempItem.getDiscountPrice().multiply(BigInteger.valueOf(discountedGroups))));

        BigInteger priceOfRestItems =
                new BigInteger(String.valueOf(tempItem.getStockPrice().multiply(BigInteger.valueOf(restWithoutDiscount))));

        priceWithDiscount = priceWithDiscount.add(discountedGroupsPrice.add(priceOfRestItems));

        return priceWithDiscount;
    }



}
