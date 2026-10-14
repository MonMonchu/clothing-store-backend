package com.clothingstore.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id" , nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order",
    cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private String shippingAddress;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected Order(){

    }
    public Order(Customer customer, PaymentMethod paymentMethod, String shippingAddress ){

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method required");
        }
        this.customer = customer;
        this.orderStatus = OrderStatus.PENDING;
        this.paymentStatus = PaymentStatus.PENDING;
        this.paymentMethod = paymentMethod;
        this.shippingAddress = shippingAddress;

    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }


    public void setShippingAddress(String address){
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }
        this.shippingAddress = address;
    }
    public void markAsPaid() {
        this.paymentStatus = PaymentStatus.PAID;
    }

    public void markAsShipped() {
        this.orderStatus = OrderStatus.SHIPPED;
    }

    public UUID getId(){
        return id;
    }
    public Customer getCustomer(){
        return customer;
    }

    public List<OrderItem> getItems(){
        return List.copyOf(items);
    }

    public BigDecimal getTotalPrice(){
        return totalPrice;
    }

    public PaymentMethod getPaymentMethod(){
        return paymentMethod;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public String getShippingAddress(){
        return shippingAddress;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }


    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        for (OrderItem existing : items) {
            if (existing.getProductVariant().getId()
                    .equals(item.getProductVariant().getId())) {

                existing.increaseQuantity(item.getQuantity());
                recalculateTotal();
                return;
            }
        }

        items.add(item);
        item.setOrder(this);
        recalculateTotal();
    }
    public void removeItem(OrderItem item){
        if (item == null){
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (items.remove(item)) {
            item.setOrder(null);
        }
        recalculateTotal();

    }
    private void recalculateTotal(){
        totalPrice = items.stream()
                .map(item ->
                        item.getPriceAtPurchase()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}



