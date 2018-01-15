package dbUnit;

import com.mysql.cj.jdbc.MysqlDataSource;
import config.DatabaseConnector;
import controller.DatabaseReturnMethods;
import dao.JdbcItemDao;
import dto.Item;
import mapper.BasketItemRowMapper;
import mapper.ItemRowMapper;
import org.apache.log4j.PropertyConfigurator;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;

import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.*;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import static org.h2.engine.Constants.UTF8;
import static org.mockito.Mockito.when;


public class XmlDatabaseTest {

    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private String log4jConfPath = "src/main/resources/db.properties";
    private PropertyConfigurator propertyConfigurator = new PropertyConfigurator();
    ItemRowMapper itemRowMapper ;
    BasketItemRowMapper basketItemRowMapper ;
    JdbcTemplate jdbcTemplate;
    List<Item> testStockList;
    JdbcItemDao jdbcItemDao;
    DatabaseReturnMethods databaseReturnMethods;
    JdbcDataSource jdbcDataSource = dataSource();


    @BeforeClass
    public static void createSchema() throws Exception {
        RunScript.execute(JDBC_URL, USER, PASSWORD, "schema.sql", UTF8, false);
    }

    @Before
    public void importDataSet() throws Exception {
        IDataSet dataSet = readDataSet();
        cleanlyInsert(dataSet);
        setUp();
    }

    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("stockDatabase.xml"));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    private JdbcDataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(JDBC_URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    public void setUp(){
        jdbcTemplate = new JdbcTemplate();
        basketItemRowMapper = new BasketItemRowMapper();
        itemRowMapper = new ItemRowMapper();
        jdbcTemplate.setDataSource(dataSource());
        jdbcItemDao = new JdbcItemDao(jdbcTemplate, itemRowMapper, basketItemRowMapper);
        testStockList = jdbcItemDao.getAllStockItems();
        databaseReturnMethods = new DatabaseReturnMethods();

    }


    @Test
    public void shouldCheckIfThereIsTvInStockList() throws Exception {
        assertEquals("LEDTv", testStockList.get(0).getItemName());

  }

    @Test
    public void shouldCheckIfThereIsItemWithGivenName1() throws Exception {
        assertTrue(jdbcItemDao.checkStockItems("ledtv"));
    }

    @Test
    public void shouldCheckIfThereIsItemWithGivenName2() throws Exception {
        assertTrue(jdbcItemDao.checkStockItems("DVDPLAYER"));
    }
    @Test
    public void shouldCheckIfThereIsItemWithGivenName3() throws Exception {
        assertTrue(jdbcItemDao.checkStockItems("mp3plaYer"));
    }
    @Test
    public void shouldCheckIfThereIsItemWithGivenName4() throws Exception {
        assertTrue(jdbcItemDao.checkStockItems("mobilephone"));
    }
    @Test
    public void shouldCheckIfThereIsItemReturnFalse() throws Exception {
        assertFalse(jdbcItemDao.checkStockItems("mobildsephone"));
    }

    @Test
    public void shouldCheckIfBasketExistAndReturnFalse(){
        assertFalse(jdbcItemDao.checkBasketExists(dataSource()));
    }

    @Test
    public void shouldCheckIfBasketExistAndReturnTrue(){
        jdbcItemDao.createNewBasket();
        assertFalse(jdbcItemDao.checkBasketExists(dataSource()));
    }

    @Test
    public void shouldCalculateDiscountedPriceFor6TV(){
        jdbcItemDao.createNewBasket();
        jdbcItemDao.addToBasket("ledTv",6);
        assertEquals(new BigInteger("140"),jdbcItemDao.getTotalDiscountedPrice(dataSource()));
    }


    @Test
    public void shouldCalculateDiscountedPriceAndReturn555(){
        jdbcTemplate.execute("drop table if exists basket_list");
        jdbcItemDao.createNewBasket();
        jdbcItemDao.addToBasket("ledTv",6);
        jdbcItemDao.addToBasket("MP3PLAYER",6);
        jdbcItemDao.addToBasket("mobilePHONE", 11);
        jdbcItemDao.addToBasket("dvdPLAYER",8);
        jdbcItemDao.addToBasket("mp3player",3);
        assertEquals(new BigInteger("555"),jdbcItemDao.getTotalDiscountedPrice(dataSource()));
    }

    @Test
    public void shouldCheckBasketItemsAndReturnId(){
        jdbcItemDao.createNewBasket();
        jdbcItemDao.addToBasket("ledTv",6);
        assertEquals(1,jdbcItemDao.checkBasketItems("LEDTv"));
    }

    @Test
    public void shouldCheckBasketItemsAndReturnMinusOne(){
        jdbcItemDao.createNewBasket();
        assertEquals(-1,  jdbcItemDao.checkBasketItems("xxxx"));
    }

    @Test
    public void shouldUpdateQuantityOfTV(){
        jdbcTemplate.execute("drop table if exists basket_list");
        jdbcItemDao.createNewBasket();
        jdbcItemDao.addToBasket("ledTv",2);
        jdbcItemDao.updateQuantity(1,3);
        Integer indexInBasket = (jdbcItemDao.checkBasketItems("LEDTV") - 1);
        Integer quantityInBasket = jdbcItemDao.getAllBasketItems().get(indexInBasket).getInBasketQuantity();
        assertEquals(new Integer(5),  quantityInBasket);
    }

    @Test
    public void shouldUpdateQuantityOfMp3PLayer(){
        jdbcTemplate.execute("drop table if exists basket_list");
        jdbcItemDao.createNewBasket();
        jdbcItemDao.addToBasket("ledTv",2);
        jdbcItemDao.addToBasket("dvdplayer",3);
        jdbcItemDao.addToBasket("mp3plaYER",12);
        jdbcItemDao.updateQuantity(3,3);
        Integer indexInBasket = (jdbcItemDao.checkBasketItems("mp3player") - 1);
        Integer quantityInBasket = jdbcItemDao.getAllBasketItems().get(indexInBasket).getInBasketQuantity();
        assertEquals(new Integer(15),  quantityInBasket);
    }

}






