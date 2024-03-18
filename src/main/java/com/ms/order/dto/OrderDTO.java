package com.ms.order.dto;

import com.ms.order.model.Order;
import com.ms.order.validation.ValidDateFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {

    private String id;

    @ValidDateFormat(message = "The order date must be in day, month, and year format with 4 digits for year")
    private String orderDate;

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]+\s[A-Z][a-z]+$",
            message = "O nome completo deve conter: " +
                    "Nome e sobrenome com iniciais em Letra Maiúscula!")
    private String client;

    @NotNull
    private String orderItems;

    @NotNull
    private String orderStatus;

    @NotNull
    private String orderTotal;

    @NotNull
    private String paymentMethod;

    @NotNull
    private String shippingMethod;

    @NotNull
    private String paymentInformation;

    @NotNull
    private String shippingInformation;

    @NotNull
    private String discountsAndFees;

    @NotNull
    private String orderNotes;

    private String created;
    private String updated;

    @NotNull
    private String registryUser;

    public OrderDTO(Order entity){
        BeanUtils.copyProperties(entity, this);
    }
}

