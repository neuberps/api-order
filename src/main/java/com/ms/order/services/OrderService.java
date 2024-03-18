package com.ms.order.services;

import com.ms.order.dto.OrderDTO;
import com.ms.order.exceptions.ClientNotFoundException;
import com.ms.order.exceptions.ServiceException;
import com.ms.order.model.Order;
import com.ms.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository repository;

    public List<OrderDTO> findAll() throws ServiceException {
        List<Order> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ClientNotFoundException("No clients found");
        }
        return list.stream().map(OrderDTO::new).toList();
    }
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) throws ServiceException {
        Order entity = new Order(orderDTO);
        entity.setRegistryUser(orderDTO.getRegistryUser());
        entity.setCreated(LocalDateTime.now().toString());
        repository.save(entity);
        return new OrderDTO(entity);
    }

    @Transactional
    public OrderDTO findById(String id) throws ServiceException {
        return repository.findById(id)
                .map(OrderDTO::new)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with ID: " + id));
    }

    public OrderDTO findByClient(String client) throws ServiceException {
        return repository.findByClient(client)
                .map(OrderDTO::new)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with email: " + client));
    }
    public OrderDTO update(String id, OrderDTO orderDTO) throws ServiceException {
        Optional<Order> optionalClient = repository.findById(id);
        if (optionalClient.isPresent()) {
            Order entity = optionalClient.get();
            entity.setOrderDate(orderDTO.getOrderDate());
            entity.setClient(orderDTO.getClient());
            entity.setOrderItems(orderDTO.getOrderItems());
            entity.setOrderStatus(orderDTO.getOrderStatus());
            entity.setOrderTotal(orderDTO.getOrderTotal());
            entity.setPaymentMethod(orderDTO.getPaymentMethod());
            entity.setShippingMethod(orderDTO.getShippingMethod());
            entity.setPaymentInformation(orderDTO.getPaymentInformation());
            entity.setDiscountsAndFees(orderDTO.getDiscountsAndFees());
            entity.setOrderNotes(orderDTO.getOrderNotes());
            entity.setCreated(orderDTO.getCreated());
            entity.setUpdated(LocalDateTime.now().toString());
            entity.setRegistryUser(orderDTO.getRegistryUser());

            repository.save(entity);
            return new OrderDTO(entity);
        } else {
            throw new ClientNotFoundException("Client not found with ID: " + id);
        }
    }

    public void delete(String id) throws ServiceException {
        if (!repository.existsById(id)) {
            throw new ClientNotFoundException("Client not found with ID: " + id);
        }
        repository.deleteById(id);
    }

}
