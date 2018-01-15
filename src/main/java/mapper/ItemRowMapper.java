package mapper;

import dto.Item;

import org.springframework.jdbc.core.RowMapper;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRowMapper implements RowMapper<Item> {


    @Override
    public Item mapRow(ResultSet resultSet, int i) throws SQLException {

        int stock_id = resultSet.getInt("stock_id");
        String item_name = resultSet.getString("item_name");
        BigInteger stock_price = BigInteger.valueOf(resultSet.getInt("stock_price"));
        Integer units_for_discount = resultSet.getInt("units_for_discount");
        BigInteger discount_price = BigInteger.valueOf(resultSet.getInt("discount_price"));

        Item item = new Item(stock_id, item_name, stock_price, units_for_discount, discount_price);


        return item;
    }
}
