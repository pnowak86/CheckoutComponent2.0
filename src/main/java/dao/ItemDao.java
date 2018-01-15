package dao;

import dto.BasketItem;
import dto.Item;

import javax.activation.DataSource;
import java.math.BigInteger;
import java.util.List;

public interface ItemDao {

    boolean checkStockItems(String itemName);

    int checkBasketItems(String itemName);

    boolean createNewBasket();

    boolean checkBasketExists(Object dataSource);

    boolean addToBasket(String item_name, Integer quantity);

    boolean updateQuantity(Integer itemId, Integer quantity);

    BigInteger getTotalDiscountedPrice(Object dataSource);

    List<Item> getAllStockItems();

    List<BasketItem> getAllBasketItems();
}
