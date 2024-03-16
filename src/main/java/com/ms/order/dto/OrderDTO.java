package com.ms.order.dto;

import com.ms.order.model.Order;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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


    @Constraint(validatedBy = ValidDateFormatValidator.class)
    @java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface ValidDateFormat {
        String message() default "Invalid date format";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public static class ValidDateFormatValidator implements ConstraintValidator<ValidDateFormat, String> {
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) {
                return false;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                Date date = sdf.parse(value);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
    }
}
