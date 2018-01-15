package dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import config.DatabaseConnector;
import controller.DatabaseReturnMethods;
import dto.BasketItem;
import dto.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import priceCalculator.PriceCalculatorInterface;
import priceCalculator.DiscountCalculator;


import javax.activation.DataSource;
import java.math.BigInteger;
import java.util.List;

public class JdbcItemDao implements ItemDao {
    private static final String SELECT_ALL_STOCK_ITEMS = "SELECT * from stock_list";
    private static final String SELECT_ALL_BASKET_ITEMS = "SELECT * from basket_list";
    private static final String CREATE_NEW_BASKET = "CREATE TABLE IF NOT EXISTS" +
            " basket_list (\n" +
            "basket_id int AUTO_INCREMENT PRIMARY KEY,\n" +
            "basket_item_name CHAR(20) NOT NULL,\n" +
            "basket_quantity INT NOT NULL,\n" +
            "basket_price_with_discount INT NOT NULL\n" +
            ");";
    private static final String INSERT_INTO_BASKET = "INSERT INTO basket_list(basket_item_name, basket_quantity, basket_price_with_discount) VALUES(?, ?, ?);";
    private static final String UPDATE_QUANTITY_IN_BASKET = "UPDATE basket_list SET basket_quantity=?, basket_price_with_discount=? WHERE basket_id=?;";


    private static final String databaseName = DatabaseConnector.getDatabaseName();
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Item> itemRowMapper;
    private RowMapper<BasketItem> basketItemRowMapper;
    private List<Item> tempStockList;
    private PriceCalculatorInterface priceCalculatorInterface;


    public JdbcItemDao() {
        this.tempStockList = getAllStockItems();

    }

    public JdbcItemDao(JdbcTemplate jdbcTemplate, RowMapper<Item> itemRowMapper, RowMapper<BasketItem> basketItemRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.itemRowMapper = itemRowMapper;
        this.basketItemRowMapper = basketItemRowMapper;
        this.tempStockList = getAllStockItems();

    }


    @Override
    public boolean checkStockItems(String itemName) {
        for (Item item : tempStockList) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int checkBasketItems(String itemName) {
        List<BasketItem> tempBasketList = getAllBasketItems();
        for (BasketItem basketItem : tempBasketList) {
            if (basketItem.getBasketItemName().equalsIgnoreCase(itemName)) {
                return basketItem.getBasketId();
            }
        }
        return -1;
    }



    @Override
    public BigInteger getTotalDiscountedPrice(Object dataSource) {
        return DatabaseReturnMethods.sumAllDiscountedPrices(dataSource);
    }


    @Override
    public boolean createNewBasket() {
        jdbcTemplate.execute(CREATE_NEW_BASKET);
            return true;

    }

    @Override
    public boolean checkBasketExists(Object dataSource) {
        return DatabaseReturnMethods.checkIfBasketTableExists(dataSource);
    }


    @Override
    public boolean addToBasket(String itemName, Integer quantity) {
        boolean checkStock = checkStockItems(itemName);
        if (checkStock && (quantity != 0)) {
            Integer itemIdInBasket = checkBasketItems(itemName);
            if (itemIdInBasket >= 0) {
                updateQuantity(itemIdInBasket, quantity);
                return true;
            } else {
                BigInteger priceWithDiscount = calcTotalDiscountedPriceForUnits(itemName,quantity);
                jdbcTemplate.update(INSERT_INTO_BASKET, itemName, quantity, priceWithDiscount);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateQuantity(Integer itemId, Integer quantity) {
        List<BasketItem> tempBasketList = getAllBasketItems();
        Integer currentQuantity = 0;

        for (BasketItem basketItem : tempBasketList) {
            if (basketItem.getBasketId() == itemId) {
                currentQuantity = basketItem.getInBasketQuantity();
                Integer quantityToPut = quantity + currentQuantity;
                String basketItemName = basketItem.getBasketItemName();
                BigInteger priceWithDiscount = calcTotalDiscountedPriceForUnits(basketItemName,quantityToPut);
                jdbcTemplate.update(UPDATE_QUANTITY_IN_BASKET, quantityToPut, priceWithDiscount, itemId);
                return true;
            }
        }
        return false;

    }

    public BigInteger calcTotalDiscountedPriceForUnits(String basketItemName, Integer quantity){
        BigInteger priceWithDiscount = new BigInteger("0");
        priceCalculatorInterface = new DiscountCalculator();
        priceWithDiscount = priceCalculatorInterface.discountForUnits(basketItemName, quantity);
        return priceWithDiscount;
    }



    @Override
    public List<Item> getAllStockItems() {
        return jdbcTemplate.query(SELECT_ALL_STOCK_ITEMS, itemRowMapper);
    }

    @Override
    public List<BasketItem> getAllBasketItems() {
        return jdbcTemplate.query(SELECT_ALL_BASKET_ITEMS, basketItemRowMapper);
    }
}
