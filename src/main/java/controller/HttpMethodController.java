package controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.MysqlDataSource;
import config.DatabaseConnector;
import dao.JdbcItemDao;
import dto.BasketItem;
import dto.Item;
import mapper.BasketItemRowMapper;
import mapper.ItemRowMapper;

import javax.activation.DataSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class HttpMethodController extends HttpServlet {
    private ObjectMapper objectMapper;
    private ItemRowMapper itemRowMapper;
    private BasketItemRowMapper basketItemRowMapper;
    private JdbcItemDao jdbcItemDao;
    private List<BasketItem> basketItems;
    private static MysqlDataSource dataSource = DatabaseConnector.getDataSourceField();
    ;

    public HttpMethodController() {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        itemRowMapper = new ItemRowMapper();
        basketItemRowMapper = new BasketItemRowMapper();

        jdbcItemDao = new JdbcItemDao(DatabaseConnector.getMySQLDataSource(), itemRowMapper, basketItemRowMapper);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String showParameter = req.getParameter("show");

        if (showParameter.equalsIgnoreCase("stock")) {
            List<Item> itemList = jdbcItemDao.getAllStockItems();
            for (Item item : itemList) {
                String itemsAsString = objectMapper.writeValueAsString(item);
                out.println(itemsAsString);
                resp.setStatus(200);
            }
        } else if (showParameter.equalsIgnoreCase("basket")) {
            if (jdbcItemDao.checkBasketExists(dataSource)) {
                basketItems = jdbcItemDao.getAllBasketItems();
                if (basketItems.size() > 0) {
                    for (BasketItem basketItem : basketItems) {
                        String itemsAsString = objectMapper.writeValueAsString(basketItem);
                        out.println(itemsAsString);
                        resp.setStatus(200);
                    }
                    out.println("Total price to pay (with discount): " + jdbcItemDao.getTotalDiscountedPrice(dataSource));
                } else {
                    out.println("Basket exists, but there is nothing in it! Yet.");
                }
            } else if ((!jdbcItemDao.checkBasketExists(dataSource))) {
                jdbcItemDao.createNewBasket();
                out.println("Created new basket!");
                resp.setStatus(200);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String nameParameter = req.getParameter("name");
        Integer quantityParameter = Integer.valueOf(req.getParameter("quantity"));


        if (!jdbcItemDao.checkBasketExists(dataSource)) {
            jdbcItemDao.createNewBasket();
            out.println("Created new basket!");
            if (jdbcItemDao.addToBasket(nameParameter, quantityParameter)) {
                out.println("Succesfully added!");
                resp.setStatus(200);
            } else {
                out.println("Something wrong, not added to basket");
            }

        }else if(jdbcItemDao.checkBasketExists(dataSource)){
            if (jdbcItemDao.addToBasket(nameParameter, quantityParameter)) {
                out.println("Succesfully added!");
                resp.setStatus(200);
            } else {
                out.println("Something wrong, not added to basket");
            }
        }
    }
}
