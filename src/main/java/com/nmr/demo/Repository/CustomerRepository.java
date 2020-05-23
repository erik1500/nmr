package com.nmr.demo.Repository;

import com.nmr.demo.Model.Customer;
import com.nmr.demo.Util.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements ICustomerRepository {

    private Connection conn;

    public CustomerRepository(){
        this.conn = DatabaseConnectionManager.getDatabaseConnection();
    }

    @Override
    public void createCustomer(Customer c) {

    }

    @Override
    public Customer readOneCustomer(int customer_id) {
        Customer customerToReturn = new Customer();
        try {
            String getCustomer = "SELECT customer_table.customer_id,customer_table.customer_first_name,customer_table.customer_last_name, " +
                    "address_table.address_streetname, address_table.address_city, address_table.address_zipcode,address_table.address_country, " +
                    "email_table.email, " +
                    "phonenumber_table.phonenumber, "+
                    "customer_dob,customer_driverslicense_id " +
                    "FROM nmr.customer_table "+
                    "INNER JOIN nmr.address_table ON customer_table.customer_id = address_table.address_id " +
                    "INNER JOIN nmr.email_table ON customer_table.customer_id = email_table.email_id " +
                    "INNER JOIN nmr.phonenumber_table ON customer_table.customer_id = phonenumber_table.phonenumber_id WHERE customer_id=? ";
            PreparedStatement myStatement = conn.prepareStatement(getCustomer);
            myStatement.setInt(1,customer_id);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                customerToReturn = new Customer(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getDate(10).toString(),
                        rs.getString(11));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerToReturn;
    }

    @Override
    public List<Customer> readAllCustomers() {
        List <Customer> allCustomers = new ArrayList<>();
        try {
            String getAllCustomer = "SELECT customer_table.customer_id,customer_table.customer_first_name,customer_table.customer_last_name, " +
                    "address_table.address_streetname, address_table.address_city, address_table.address_zipcode,address_table.address_country, " +
                    "email_table.email, " +
                    "phonenumber_table.phonenumber, "+
                    "customer_dob,customer_driverslicense_id " +
                    "FROM nmr.customer_table "+
                    "INNER JOIN nmr.address_table ON customer_table.customer_id = address_table.address_id " +
                    "INNER JOIN nmr.email_table ON customer_table.customer_id = email_table.email_id " +
                    "INNER JOIN nmr.phonenumber_table ON customer_table.customer_id = phonenumber_table.phonenumber_id";
            PreparedStatement myStatement = conn.prepareStatement(getAllCustomer);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                allCustomers.add(new Customer(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getDate(10).toString(),
                        rs.getString(11)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    @Override
    public void updateCustomer(Customer c) {
        try {
            String query = "UPDATE nmr.customer_table " +
                    "INNER JOIN nmr.address_table ON customer_table.customer_id = address_table.address_id " +
                    "INNER JOIN nmr.email_table ON customer_table.customer_id = email_table.email_id " +
                    "INNER JOIN nmr.phonenumber_table ON customer_table.customer_id = phonenumber_table.phonenumber_id " +
                    "SET customer_first_name=?, customer_last_name=?, address_table.address_streetname=?" +
                    ",address_table.address_city=?, address_table.address_zipcode=?," +
                    "address_table.address_country=?,email_table.email=?,phonenumber_table.phonenumber=?,customer_dob=?" +
                    ",customer_driverslicense_id=? WHERE customer_id=?";

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1,c.getFirstName());
            preparedStatement.setString(2, c.getLastName());
            preparedStatement.setString(3,c.getAddressStreetname());
            preparedStatement.setString(4,c.getAddressCity());
            preparedStatement.setInt(5,c.getAddressZipcode());
            preparedStatement.setString(6,c.getAddressCountry());
            preparedStatement.setString(7,c.getEmail());
            preparedStatement.setString(8,c.getPhonenumber());
            preparedStatement.setDate(9,java.sql.Date.valueOf(c.getDob()));
            preparedStatement.setString(10,c.getDriverslicense());
            preparedStatement.setInt(11,c.getCustomer_id());

            preparedStatement.execute();
            preparedStatement.close();
        }catch (SQLException s){
            s.printStackTrace();
        }
    }

    @Override
    public boolean deleteCustomer(int id) {
        return false;
    }

}