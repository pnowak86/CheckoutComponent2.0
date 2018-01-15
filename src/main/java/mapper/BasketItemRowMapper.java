package mapper;

import dto.BasketItem;

import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BasketItemRowMapper implements RowMapper<BasketItem> {


    @Override
    public BasketItem mapRow(ResultSet resultSet, int i) throws SQLException {

        int basket_id = resultSet.getInt("basket_id");
        String basket_item_name = resultSet.getString("basket_item_name");
        Integer basket_quantity = resultSet.getInt("basket_quantity");
        BigInteger basket_price_with_discount =
                BigInteger.valueOf(resultSet.getInt("basket_price_with_discount"));

        BasketItem basketItem = new BasketItem(basket_id,basket_item_name, basket_quantity, basket_price_with_discount);

        return basketItem;
    }
}

