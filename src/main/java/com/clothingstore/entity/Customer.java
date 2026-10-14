package com.clothingstore.entity;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String address;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();



    protected Customer(){
    }

    public Customer(String email, String password,String firstName, String lastName, String address){
        this.password = password;
        this.email = email  != null ? email.toLowerCase() : null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;

    }

    public void setEmail(String email){
        this.email = email  != null ? email.toLowerCase() : null;
    }


    public void setFirstName(String firstName){

        this.firstName= firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setAddress(String address){

        this.address = address;
    }

    public UUID getId(){
        return id;
    }

    public String getEmail(){
        return email;
    }


    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
    public String getAddress(){
        return address;
    }
    public List<Order> getOrders(){
        return Collections.unmodifiableList(orders);
    }

    public void changePassword(String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password = hashedPassword;
    }

    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (order.getCustomer() != null && !order.getCustomer().equals(this)) {
            throw new IllegalStateException("Order already belongs to another customer");
        }

        orders.add(order);
        order.setCustomer(this);
    }

    public void removeOrder(Order order){
        if (order == null){
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (orders.remove(order)) {
            order.setCustomer(null);
        }
    }
    @Override
    public boolean equals(Object o){
        if (this == o)return true;
        if (!(o instanceof Customer)) return false;
        Customer other = (Customer) o;
        return id!= null && id.equals(other.id);
    }
    @Override
    public int hashCode(){
        return getClass().hashCode();
    }

}
