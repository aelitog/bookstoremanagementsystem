
package bookstoremanagementsystem.loginfolder;
//Cashier User Controller
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import databaseclasses.Book;
import databaseclasses.Customer;
import databaseclasses.DbClass;
import databaseclasses.Rent;
import databaseclasses.Sale;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;


public class LoginCashierController implements Initializable {
    //Database connection
    Connection connection = DbClass.connect();
    //To show data in tableviews
    ObservableList<Book> data = FXCollections.observableArrayList();
    ObservableList<Customer> customerdata = FXCollections.observableArrayList();
    ObservableList<Sale> saledata = FXCollections.observableArrayList();
    ObservableList<Rent> rentdata = FXCollections.observableArrayList();
    // Query statements and results
    PreparedStatement preparedStatement = null;
    ResultSet result = null;
    PreparedStatement priceStatement = null;
    ResultSet priceResult = null;
    PreparedStatement quantitypreparedStatement = null;
    ResultSet quantityresult = null;
    PreparedStatement rentQuantityStatement = null;
    ResultSet rentQuantityresult = null;
    PreparedStatement rentquantitypreparedStatement = null;

    @FXML
    private JFXTextField cusID, cusName, cusSurname, cusEmail, cusPhone, cusIdNo;
    @FXML
    private TableView<Book> searchTable;
    @FXML
    private TableColumn<?, ?> sTableISBN, sTableName, sTableAuthor, sTablePublisher, sTablePrice, sTablePages, sTableLang, sTableCategory, sTableSupplier, sTableQuantity, sTableRentQuantity;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<?, ?> customerID, customerName, customerSurname, customerEmail, customerPhone, customerIdentity;
    @FXML
    private TableView<Sale> saleTable;
    @FXML
    private TableColumn<?, ?> saleISBN, saleDate, saleAmount, saleTotalPrice;
    @FXML
    private JFXTextField saleTextISBN, saleTextAmount;
    @FXML
    private Label saleLabelText, rentLabelText, incomeLabel;

    @FXML
    private TableView<Rent> rentTable;
    @FXML
    private TableColumn<?, ?> rentISBN, rentStartingDate, rentEndDate, rentCustomerID, rentPrice;
    @FXML
    private JFXTextField rentISBNText, rentCustomerText;
    @FXML
    private DatePicker startingDate, endDate, incomeStart, incomeEnd;

    private double tempTotal = 0, tempRentTotal = 0;

    private int tempCount = 0, tempRentCount = 0;

    @FXML
    private JFXRadioButton radioISBN, radioName, radioAuthor, radioPublisher, radioPrice, radioPages, radioLang, radioCategory, radioSupplier, radioQuantity, radioRentQuantity;

    @FXML
    private JFXTextField searchText;
    @FXML
    private Label searchExample;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerInterface();
         searchExample.setText("For string; \n\t\tX% -> start with X\n\t\t%X -> end with X\n\t\t%X% -> includes X");
    }
    //To show all data "ALL" button action 
    public void SearchAllhandle(ActionEvent event) {
        deleteTableView(searchTable);

        LoadDatabaseData();
    }

    public void LoadDatabaseData() {
        sTableISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        sTableName.setCellValueFactory(new PropertyValueFactory<>("book_name"));
        sTableAuthor.setCellValueFactory(new PropertyValueFactory<>("author_name"));
        sTablePublisher.setCellValueFactory(new PropertyValueFactory<>("book_publisher"));
        sTablePrice.setCellValueFactory(new PropertyValueFactory<>("book_price"));
        sTablePages.setCellValueFactory(new PropertyValueFactory<>("num_of_pages"));
        sTableLang.setCellValueFactory(new PropertyValueFactory<>("book_language"));
        sTableCategory.setCellValueFactory(new PropertyValueFactory<>("book_category"));
        sTableSupplier.setCellValueFactory(new PropertyValueFactory<>("book_supplier"));
        sTableQuantity.setCellValueFactory(new PropertyValueFactory<>("book_quantity"));
        sTableRentQuantity.setCellValueFactory(new PropertyValueFactory<>("rent_quantity"));
        String query = "select * from book";
        try {
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                Book book = new Book();
                book.setISBN(result.getInt("ISBN"));
                book.setBook_name(result.getString("book_name"));
                book.setAuthor_name(result.getString("author_name"));
                book.setBook_publisher(result.getString("book_publisher"));
                book.setBook_price(result.getDouble("book_price"));
                book.setNum_of_pages(result.getInt("num_of_pages"));
                book.setBook_language(result.getString("book_language"));
                book.setBook_category(result.getString("book_category"));
                book.setBook_supplier(result.getString("book_supplier"));
                book.setBook_quantity(result.getInt("book_quantity"));
                book.setRent_quantity(result.getInt("rent_quantity"));

                data.add(book);
                searchTable.setItems(data);

            }
            preparedStatement.close();
            result.close();
        } catch (SQLException e) {

        }
    }
    //Customer interface tableview
    public void customerInterface() {

        customerID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        customerSurname.setCellValueFactory(new PropertyValueFactory<>("customer_surname"));
        customerEmail.setCellValueFactory(new PropertyValueFactory<>("customer_email"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("customer_phone"));
        customerIdentity.setCellValueFactory(new PropertyValueFactory<>("identity_no"));

        String query = "select * from customer";
        try {
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                Customer customer = new Customer();
                customer.setCustomer_id(result.getInt("customer_id"));
                customer.setCustomer_name(result.getString("customer_name"));
                customer.setCustomer_surname(result.getString("customer_surname"));
                customer.setCustomer_email(result.getString("customer_email"));
                customer.setCustomer_phone(result.getInt("customer_phone"));
                customer.setIdentity_no(result.getInt("identity_no"));
                customerdata.add(customer);
                customerTable.setItems(customerdata);

            }
            preparedStatement.close();
            result.close();
        } catch (SQLException e) {

        }
    }
    //Customer interface add button action
    public void customerAddHandle(ActionEvent event) {
        int tempID = Integer.parseInt(cusID.getText());
        String tempName = cusName.getText();
        String tempSurname = cusSurname.getText();
        String tempEmail = cusEmail.getText();
        int tempPhone = Integer.parseInt(cusPhone.getText());
        int tempIdentity = Integer.parseInt(cusIdNo.getText());

        String sql = "INSERT INTO customer(customer_id, customer_name, customer_surname,customer_email, customer_phone, identity_no) VALUES(?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tempID);
            preparedStatement.setString(2, tempName);
            preparedStatement.setString(3, tempSurname);
            preparedStatement.setString(4, tempEmail);
            preparedStatement.setInt(5, tempPhone);
            preparedStatement.setInt(6, tempIdentity);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            result.close();
            deleteTableView(customerTable);
            customerInterface();
            cusID.clear();
            cusName.clear();
            cusSurname.clear();
            cusEmail.clear();
            cusPhone.clear();
            cusIdNo.clear();

        } catch (SQLException e) {

        }
    }
    //Sale interface add button action
    public void saleAddHandle(ActionEvent event) {

        saleISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        saleDate.setCellValueFactory(new PropertyValueFactory<>("sale_date"));
        saleAmount.setCellValueFactory(new PropertyValueFactory<>("sale_quantity"));
        saleTotalPrice.setCellValueFactory(new PropertyValueFactory<>("sale_price"));

        int tempSaleISBN = Integer.parseInt(saleTextISBN.getText());
        int tempSaleAmount = Integer.parseInt(saleTextAmount.getText());

        String sqlPrice = "select * from book where ISBN = " + tempSaleISBN;

        try {

            priceStatement = connection.prepareStatement(sqlPrice);
            priceResult = priceStatement.executeQuery();
            String sql = "INSERT INTO sale(ISBN, sale_quantity, sale_price, sale_date) VALUES(?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            if (priceResult.getInt("book_quantity") > tempSaleAmount) {
                preparedStatement.setInt(1, tempSaleISBN);
                preparedStatement.setInt(2, tempSaleAmount);
                preparedStatement.setDouble(3, (priceResult.getInt("book_price") * tempSaleAmount));
                preparedStatement.setString(4, LocalDate.now().toString());
                preparedStatement.executeUpdate();
                saleLabelText.setText(" ");
                tempTotal += (priceResult.getInt("book_price") * tempSaleAmount);
                tempCount++;

                String sqlQuantity = "UPDATE book set book_quantity = ? where ISBN = " + tempSaleISBN;
                quantitypreparedStatement = connection.prepareStatement(sqlQuantity);
                quantitypreparedStatement.setInt(1, (priceResult.getInt("book_quantity") - tempSaleAmount));
                quantitypreparedStatement.executeUpdate();
                quantitypreparedStatement.close();

            } else {
                saleLabelText.setText("Not enough book quantity!");
                saleLabelText.setTextFill(Color.RED);
            }
            saleTextISBN.clear();
            saleTextAmount.clear();
            preparedStatement.close();
            priceStatement.close();
            priceResult.close();
            deleteTableView(saleTable);
            String sql2 = "select * from sale desc limit " + tempCount;
            preparedStatement = connection.prepareStatement(sql2);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                Sale sale = new Sale();
                sale.setISBN(result.getInt("ISBN"));
                sale.setSale_quantity(result.getInt("sale_quantity"));
                sale.setSale_price(result.getDouble("sale_price"));
                sale.setSale_date(result.getString("sale_date"));
                saledata.add(sale);
                saleTable.setItems(saledata);
            }
            preparedStatement.close();
            result.close();

        } catch (SQLException e) {

        }

    }
    //Rent interface add button aciton 
    public void rentAddHandle(ActionEvent event) {

        rentCustomerID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        rentISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        rentStartingDate.setCellValueFactory(new PropertyValueFactory<>("rent_starting_date"));
        rentEndDate.setCellValueFactory(new PropertyValueFactory<>("rent_end_date"));
        rentPrice.setCellValueFactory(new PropertyValueFactory<>("rent_price"));

        int tempRentISBN = Integer.parseInt(rentISBNText.getText());
        int tempCustomerID = Integer.parseInt(rentCustomerText.getText());
        LocalDate tempStartDate = startingDate.getValue();
        LocalDate tempEndDate = endDate.getValue();
        Period intervalPeriod = Period.between(tempStartDate, tempEndDate);
        int diffrence = intervalPeriod.getDays();

        String sqlRent = "select * from book where ISBN = " + tempRentISBN;

        try {
            rentQuantityStatement = connection.prepareStatement(sqlRent);
            rentQuantityresult = rentQuantityStatement.executeQuery();
            double temptotalprice = rentQuantityresult.getInt("book_price") * diffrence * 0.02;

            String sql = "INSERT INTO rent(customer_id, ISBN, rent_starting_date, rent_end_date, rent_price) VALUES(?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            if (rentQuantityresult.getInt("rent_quantity") != 0 && tempStartDate.isBefore(tempEndDate)) {
                preparedStatement.setInt(1, tempCustomerID);
                preparedStatement.setInt(2, tempRentISBN);
                preparedStatement.setString(3, tempStartDate.toString());
                preparedStatement.setString(4, tempEndDate.toString());
                preparedStatement.setDouble(5, temptotalprice);
                preparedStatement.executeUpdate();
                tempRentTotal += temptotalprice;
                tempRentCount++;
                String sqlQuantity = "UPDATE book set rent_quantity = ? where ISBN = " + tempRentISBN;
                rentquantitypreparedStatement = connection.prepareStatement(sqlQuantity);
                rentquantitypreparedStatement.setInt(1, (rentQuantityresult.getInt("rent_quantity") - 1));
                rentquantitypreparedStatement.executeUpdate();
                rentquantitypreparedStatement.close();

            } else {
                rentLabelText.setText("Invalid values!");
                rentLabelText.setTextFill(Color.RED);
            }
            rentISBNText.clear();
            rentCustomerText.clear();
            preparedStatement.close();
            rentQuantityresult.close();
            deleteTableView(rentTable);
            String sql3 = "select * from rent where customer_id =  " + tempCustomerID;
            preparedStatement = connection.prepareStatement(sql3);
            result = preparedStatement.executeQuery();

            while (result.next()) {
                Rent rent = new Rent();
                rent.setCustomer_id(result.getInt("customer_id"));
                rent.setISBN(result.getInt("ISBN"));
                rent.setRent_starting_date(result.getString("rent_starting_date"));
                rent.setRent_end_date(result.getString("rent_end_date"));
                rent.setRent_price(result.getDouble("rent_price"));

                rentdata.add(rent);
                rentTable.setItems(rentdata);
                preparedStatement.close();
                result.close();
            }
        } catch (SQLException e) {

        }

    }
    //Income interface search button action
    public void incomeSearchHandle(ActionEvent event) {
        double tempTotal =0;
        String tempLabel = "Rent Income : ";
        LocalDate startDate = incomeStart.getValue();
        LocalDate endDate = incomeEnd.getValue();
        String stringStartDate = startDate.toString();
        String stringEndDate = endDate.toString();
        String sql = "select sum(rent_price) as price from rent where rent_starting_date between '" + stringStartDate + "' and '" + stringEndDate + "'";
        String sql2 = "select sum(sale_price) as price from sale where sale_date between '" + stringStartDate + "' and '" + stringEndDate + "'";
        try {
            preparedStatement = connection.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            tempTotal+=result.getDouble("price");
            tempLabel+=result.getDouble("price");
            tempLabel+= "\nSale Income : ";
            preparedStatement.close();
            result.close();
            preparedStatement = connection.prepareStatement(sql2);
            result = preparedStatement.executeQuery();
            tempLabel+=result.getDouble("price");
            tempTotal+=result.getDouble("price");
            tempLabel+="\nTotal Income : " + tempTotal;
            incomeLabel.setText(tempLabel);
        } catch (SQLException e) {

        }
    }
    //Search interface search button action
     public void searchHandle(ActionEvent event) {
        deleteTableView(searchTable);
        sTableISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        sTableName.setCellValueFactory(new PropertyValueFactory<>("book_name"));
        sTableAuthor.setCellValueFactory(new PropertyValueFactory<>("author_name"));
        sTablePublisher.setCellValueFactory(new PropertyValueFactory<>("book_publisher"));
        sTablePrice.setCellValueFactory(new PropertyValueFactory<>("book_price"));
        sTablePages.setCellValueFactory(new PropertyValueFactory<>("num_of_pages"));
        sTableLang.setCellValueFactory(new PropertyValueFactory<>("book_language"));
        sTableCategory.setCellValueFactory(new PropertyValueFactory<>("book_category"));
        sTableSupplier.setCellValueFactory(new PropertyValueFactory<>("book_supplier"));
        sTableQuantity.setCellValueFactory(new PropertyValueFactory<>("book_quantity"));
        sTableRentQuantity.setCellValueFactory(new PropertyValueFactory<>("rent_quantity"));
        String sql = "";
        String tempText = searchText.getText();
        if (radioISBN.isSelected()) {
            sql = "select * from book where ISBN = '" + tempText + "'";
        } else if (radioName.isSelected()) {
            sql = "select * from book where book_name like  '" + tempText + "'";
        } else if (radioAuthor.isSelected()) {
            sql = "select * from book where author_name like  '" + tempText + "'";
        } else if (radioPublisher.isSelected()) {
            sql = "select * from book where book_publisher like  '" + tempText + "'";
        } else if (radioPrice.isSelected()) {
            sql = "select * from book where book_price = '" + tempText + "'";
        } else if (radioPages.isSelected()) {
            sql = "select * from book where num_of_pages = '" + tempText + "'";
        } else if (radioLang.isSelected()) {
            sql = "select * from book where book_language like  '" + tempText + "'";
        } else if (radioCategory.isSelected()) {
            sql = "select * from book where book_category like  '" + tempText + "'";
        } else if (radioSupplier.isSelected()) {
            sql = "select * from book where book_supplier like  '" + tempText + "'";
        } else if (radioQuantity.isSelected()) {
            sql = "select * from book where book_quantity = '" + tempText + "'";
        } else if (radioRentQuantity.isSelected()) {
            sql = "select * from book where rent_quantity = '" + tempText + "'";
        }
        try {
            preparedStatement = connection.prepareStatement(sql);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                Book book = new Book();
                book.setISBN(result.getInt("ISBN"));
                book.setBook_name(result.getString("book_name"));
                book.setAuthor_name(result.getString("author_name"));
                book.setBook_publisher(result.getString("book_publisher"));
                book.setBook_price(result.getDouble("book_price"));
                book.setNum_of_pages(result.getInt("num_of_pages"));
                book.setBook_language(result.getString("book_language"));
                book.setBook_category(result.getString("book_category"));
                book.setBook_supplier(result.getString("book_supplier"));
                book.setBook_quantity(result.getInt("book_quantity"));
                book.setRent_quantity(result.getInt("rent_quantity"));

                data.add(book);
                searchTable.setItems(data);

            }
            preparedStatement.close();
            result.close();

        } catch (SQLException e) {

        }

    }

     //Delete tableview
    private void deleteTableView(TableView<?> tempTable) {
        for (int i = 0; i < tempTable.getItems().size(); i++) {

            tempTable.getItems().clear();
        }

    }
    //Sale interface sale button action
    public void saleButtonHandle(ActionEvent event) {
        saleLabelText.setText(tempTotal + " ");
        tempTotal = 0;
        tempCount = 0;

    }
    //Rent interface rent button action
    public void rentButtonHandle(ActionEvent event) {
        rentLabelText.setText(tempRentTotal + " ");
        tempRentTotal = 0;
        tempRentCount = 0;

    }
    //Sale interface clear button action
    public void clearButtonHandle(ActionEvent event) {
        deleteTableView(saleTable);
        saleLabelText.setText(" ");

    }
    //Rent interface clear Button action
    public void clearRentButtonHandle(ActionEvent event) {
        deleteTableView(rentTable);
        rentLabelText.setText(" ");

    }

}
