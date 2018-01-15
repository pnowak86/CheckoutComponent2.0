# CheckoutComponent2.0
New version of checkoutComponent, using mySQL database.


Set Up:

Create a MySQL database name "shop_db".

Execute shop_db.sql to create and populate the "stock_list" table:

Locate db.properties and make sure the JDBC connection string matches your database configuration


To run application simply import it to Intelij from Version Control, it has got configured and shared Maven start config "tomcatMaven", select it, and click "Run".
App will run on a local server. If you prefer you can build a *.war file from it by Maven and copy and run it from Tomcat.
To operate on app, use Postman app. Application read a stock items from file MySQL database, and place them in a List of items. 
To show them, call method Get in postman on link http://localhost:8080/CheckoutComponent/method?show=stock 
You can call method Get on link http://localhost:8080/CheckoutComponent/method?show=basket, 
to show the items that user has got in basket, for a start there will be no items in the basket, but new Object "Basket" will be created, To add an item call method Put, 
for example: http://localhost:8080/CheckoutComponent/method?name=LEDTv&quantity=3, where 'name' is a name of item from a stock list, an 'quantity' is number of items you would like to purchase,
as a confirmation of adding app will show a message and show the number of how many different positions are in in the basket.
If basket is not created, app will create it and add item to it. App will create another MySQL table called "basket_list" and will add items
to it with quantity in the basket and calculated discounted price.
When you call Get method again: http://localhost:8080/CheckoutComponent/method?show=basket it will read your Basket from MySQL table, convert it from JSON to Java Object, and show all 
Items. It will also calculate calculate discounted price and also show it on screen with message. Basked is saved in MySQL after app is closed.
For the purpose of testing I used dbUnit with H2 database, had a problem with DatabaseReturnMethods.class, so I decided to pass in parameter Object
dataSource. For use of the app, dataSource is an MysqlDataSource type, and for the tests it is JdbcDataSource type.

When we adding item to basket which is already present in there, application doesn't add it as new position, but just increase quantity in basket.
All content is now stored in MySQLDatabase, so we can easy add more Items on the stock list and application will operate on them.
