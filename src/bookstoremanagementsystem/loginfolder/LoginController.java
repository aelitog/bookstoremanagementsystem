
package bookstoremanagementsystem.loginfolder;
//Admin User Controller
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import databaseclasses.Book;
import databaseclasses.DbClass;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

public class LoginController implements Initializable {
    //Database connection
    Connection connection = DbClass.connect();
    //To show data in tableviews
    ObservableList<Book> data = FXCollections.observableArrayList();
    ObservableList<Book> supplydata = FXCollections.observableArrayList();
    //Add/delete interface textfields
    @FXML
    private JFXTextField regISBNDelete, regISBN, regName, regAuthor, regPublisher, regRentQuantity, regQuantity, regLang, regPages, regCategory, regSupplier, regPrice;
    @FXML
    private Label zoneLabel, regLabel;
    //zone amounts
    private final int redZone = 3;
    private final int yellowZone = 6;
    private int greenZone = 7;
    private int fileInt;
    @FXML
    private DatePicker incomeStart, incomeEnd; //Income interface datepickers
    @FXML
    private Label incomeLabel;

    PreparedStatement preparedStatement = null; //Query Statement
    ResultSet result = null;// Query Result
    // Search interface table view
    @FXML
    private TableView<Book> searchTable;
    @FXML
    private TableColumn<?, ?> sTableISBN, sTableName, sTableAuthor, sTablePublisher, sTablePrice, sTablePages, sTableLang, sTableCategory, sTableSupplier, sTableQuantity, sTableRentQuantity;
    // Supply interface table view
    @FXML
    private TableView<Book> supplyTable;
    @FXML
    private TableColumn<?, ?> supplyISBN, supplyName, supplyPrice, supplySupplier, supplyQuantity;
    
    String stringYellow = "", stringRed = ""; // Order.txt strings
    
    //Search interface 
    @FXML
    private JFXRadioButton radioISBN, radioName, radioAuthor, radioPublisher, radioPrice, radioPages, radioLang, radioCategory, radioSupplier, radioQuantity, radioRentQuantity;

    @FXML
    private JFXTextField searchText;
    @FXML
    private Label searchExample;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchExample.setText("For string; \n\t\tX% -> start with X\n\t\t%X -> end with X\n\t\t%X% -> includes X");
    }
    //Search interface "ALL" button action shows all data
    public void SearchAllhandle(ActionEvent event) {
        deleteTableView(searchTable);
        LoadDatabaseData();
    }
    //Search interface all button tableview
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
    //Supply interface Zone Actions
    @FXML
    public void redZoneHandle() {
        deleteTableView(supplyTable);
        fileInt = redZone;
        showZone(redZone);
    }

    @FXML
    public void yellowZoneHandle() {
        deleteTableView(supplyTable);
        fileInt = yellowZone;
        showZone(yellowZone);
    }

    @FXML
    public void greenZoneHandle() {
        deleteTableView(supplyTable);
        showZone(greenZone);
    }
    //Showing zone in tableview
    private void showZone(int zone) {
        supplyISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        supplyName.setCellValueFactory(new PropertyValueFactory<>("book_name"));
        supplyPrice.setCellValueFactory(new PropertyValueFactory<>("book_price"));
        supplySupplier.setCellValueFactory(new PropertyValueFactory<>("book_supplier"));
        supplyQuantity.setCellValueFactory(new PropertyValueFactory<>("book_quantity"));

        String query = "select * from book ";
        try {
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                Book book = new Book();

                if (zone == redZone) {
                    zoneLabel.setTextFill(Color.RED);
                    if (result.getInt("book_quantity") <= zone) {
                        book.setISBN(result.getInt("ISBN"));
                        book.setBook_name(result.getString("book_name"));
                        book.setBook_price(result.getDouble("book_price"));
                        book.setBook_supplier(result.getString("book_supplier"));
                        book.setBook_quantity(result.getInt("book_quantity"));
                        supplydata.add(book);
                        supplyTable.setItems(supplydata);
                        stringRed += result.getInt("ISBN") + "\n";
                    }

                } else if (zone == yellowZone) {
                    zoneLabel.setTextFill(Color.YELLOW);

                    if (result.getInt("book_quantity") <= zone && result.getInt("book_quantity") > redZone) {
                        book.setISBN(result.getInt("ISBN"));
                        book.setBook_name(result.getString("book_name"));
                        book.setBook_price(result.getDouble("book_price"));
                        book.setBook_supplier(result.getString("book_supplier"));
                        book.setBook_quantity(result.getInt("book_quantity"));
                        supplydata.add(book);
                        supplyTable.setItems(supplydata);
                        stringYellow += result.getInt("ISBN") + "\n";

                    }
                } else {
                    zoneLabel.setTextFill(Color.GREEN);
                    if (result.getInt("book_quantity") >= zone) {
                        book.setISBN(result.getInt("ISBN"));
                        book.setBook_name(result.getString("book_name"));
                        book.setBook_price(result.getDouble("book_price"));
                        book.setBook_supplier(result.getString("book_supplier"));
                        book.setBook_quantity(result.getInt("book_quantity"));
                        supplydata.add(book);
                        supplyTable.setItems(supplydata);

                    }
                }

            }
            preparedStatement.close();
            result.close();
        } catch (SQLException e) {

        }

    }
    //Add/Delete interface delete button action
    public void deleteHandle(ActionEvent event) {
        int tempISBN = Integer.parseInt(regISBNDelete.getText());
        String sql = "delete from book where ISBN = " + tempISBN;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            regLabel.setText("Data is removed");
            preparedStatement.close();
            result.close();
            regISBNDelete.clear();
        } catch (SQLException e) {

        }

    }
    //Add/Delete interface add button action
    public void addHandle(ActionEvent event) {
        int tempISBN = Integer.parseInt(regISBN.getText());
        String tempName = regName.getText();
        String tempAuthor = regAuthor.getText();
        String tempPublisher = regPublisher.getText();
        double tempPrice = Double.parseDouble(regPrice.getText());
        int tempPages = Integer.parseInt(regPages.getText());
        String tempLang = regLang.getText();
        String tempCategory = regCategory.getText();
        String tempSupplier = regSupplier.getText();
        int tempQuantity = Integer.parseInt(regQuantity.getText());
        int tempRentQuantity = Integer.parseInt(regRentQuantity.getText());

        String sql = "INSERT INTO book(ISBN, book_name, author_name, book_publisher, book_price, num_of_pages, book_language, book_category, book_supplier, book_quantity, rent_quantity) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tempISBN);
            preparedStatement.setString(2, tempName);
            preparedStatement.setString(3, tempAuthor);
            preparedStatement.setString(4, tempPublisher);
            preparedStatement.setDouble(5, tempPrice);
            preparedStatement.setInt(6, tempPages);
            preparedStatement.setString(7, tempLang);
            preparedStatement.setString(8, tempCategory);
            preparedStatement.setString(9, tempSupplier);
            preparedStatement.setInt(10, tempQuantity);
            preparedStatement.setInt(11, tempRentQuantity);
            preparedStatement.executeUpdate();
            regLabel.setText("Data is added");
            preparedStatement.close();

            regISBN.clear();
            regName.clear();
            regAuthor.clear();
            regCategory.clear();
            regLang.clear();
            regPages.clear();
            regPrice.clear();
            regQuantity.clear();
            regRentQuantity.clear();
            regPublisher.clear();
            regSupplier.clear();
        } catch (SQLException e) {

        }
    }
    // Income interface search button interface 
    public void incomeSearchHandle(ActionEvent event) {
        double tempTotal = 0;
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
            tempTotal += result.getDouble("price");
            tempLabel += result.getDouble("price");
            tempLabel += "\nSale Income : ";
            preparedStatement.close();
            result.close();
            preparedStatement = connection.prepareStatement(sql2);
            result = preparedStatement.executeQuery();
            tempLabel += result.getDouble("price");
            tempTotal += result.getDouble("price");
            tempLabel += "\nTotal Income : " + tempTotal;
            incomeLabel.setText(tempLabel);
        } catch (SQLException e) {

        }
    }
    //Supply interface order button action
    public void orderHandle(ActionEvent event) throws IOException {
        if (fileInt == 3) {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("order.txt"), "utf-8"))) {
                writer.write("");
                writer.write(stringRed);
            }
        } else {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("order.txt"), "utf-8"))) {
                writer.write("");
                writer.write(stringYellow);
            }
        }

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
    //Deleting Data from tableview
    private void deleteTableView(TableView<?> tempTable) {
        for (int i = 0; i < tempTable.getItems().size(); i++) {

            tempTable.getItems().clear();
        }

    }

}
