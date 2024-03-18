package com.ms.order.model;
import com.ms.order.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "orders")
public class Order implements Serializable {
    @Id
    private String id;
    private String orderDate;
    private String client;
    private String orderItems;
    private String orderStatus;
    private String orderTotal;
    private String paymentMethod;
    private String shippingMethod;
    private String paymentInformation;
    private String discountsAndFees;
    private String orderNotes;
    private String created;
    private String updated;
    private String registryUser;

    public Order(OrderDTO orderDTO){
        BeanUtils.copyProperties(orderDTO, this);
    }

    public Order() {
        super();
    }

}
