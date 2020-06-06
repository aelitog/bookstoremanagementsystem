
package databaseclasses;

public class Book {
    private int ISBN;
    private String book_name;
    private String author_name;
    private String book_publisher;
    private double book_price;
    private int num_of_pages;
    private String book_language;
    private String book_category;
    private String book_supplier;
    private int book_quantity;
    private int rent_quantity;

    public Book() {
    }
    
    
    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getBook_publisher() {
        return book_publisher;
    }

    public void setBook_publisher(String book_publisher) {
        this.book_publisher = book_publisher;
    }

    public double getBook_price() {
        return book_price;
    }

    public void setBook_price(double book_price) {
        this.book_price = book_price;
    }

    public int getNum_of_pages() {
        return num_of_pages;
    }

    public void setNum_of_pages(int num_of_pages) {
        this.num_of_pages = num_of_pages;
    }

    public String getBook_language() {
        return book_language;
    }

    public void setBook_language(String book_language) {
        this.book_language = book_language;
    }

    public String getBook_category() {
        return book_category;
    }

    public void setBook_category(String book_category) {
        this.book_category = book_category;
    }

    public String getBook_supplier() {
        return book_supplier;
    }

    public void setBook_supplier(String book_supplier) {
        this.book_supplier = book_supplier;
    }

    public int getBook_quantity() {
        return book_quantity;
    }

    public void setBook_quantity(int book_quantity) {
        this.book_quantity = book_quantity;
    }

    public int getRent_quantity() {
        return rent_quantity;
    }

    public void setRent_quantity(int rent_quantity) {
        this.rent_quantity = rent_quantity;
    }
    
}
