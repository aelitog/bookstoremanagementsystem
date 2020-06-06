
package databaseclasses;

public class Rent {
    
    private int customer_id;
    private int ISBN;
    private String rent_starting_date;
    private String rent_end_date;
    private double rent_price;

    public Rent() {
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getRent_starting_date() {
        return rent_starting_date;
    }

    public void setRent_starting_date(String rent_starting_date) {
        this.rent_starting_date = rent_starting_date;
    }

    public String getRent_end_date() {
        return rent_end_date;
    }

    public void setRent_end_date(String rent_end_date) {
        this.rent_end_date = rent_end_date;
    }

    public double getRent_price() {
        return rent_price;
    }

    public void setRent_price(double rent_price) {
        this.rent_price = rent_price;
    }

  

    
}
