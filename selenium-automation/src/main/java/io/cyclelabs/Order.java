package io.cyclelabs;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Order {
    private int orderNumber;
    private String customerName;
    private BigDecimal price;

    public Order(int orderNumber, String customerName, BigDecimal price) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.price = price;
    }

    public int getOrderNumber() {
        return this.orderNumber;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getPriceAsDollars() {
        return "$" + this.price.setScale(2, RoundingMode.UP);
    }
}
