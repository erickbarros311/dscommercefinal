package com.devsuperior.dscommercefinal.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommercefinal.dto.OrderDTO;
import com.devsuperior.dscommercefinal.dto.OrderItemDTO;
import com.devsuperior.dscommercefinal.entities.Order;
import com.devsuperior.dscommercefinal.entities.OrderItem;
import com.devsuperior.dscommercefinal.entities.OrderStatus;
import com.devsuperior.dscommercefinal.entities.Product;
import com.devsuperior.dscommercefinal.entities.User;
import com.devsuperior.dscommercefinal.repositories.OrderItemRepository;
import com.devsuperior.dscommercefinal.repositories.OrderRepository;
import com.devsuperior.dscommercefinal.repositories.ProductRepository;
import com.devsuperior.dscommercefinal.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    @Transactional
	public OrderDTO insert(OrderDTO dto) {
		
    	Order order = new Order();
    	
    	order.setMoment(Instant.now());
    	order.setStatus(OrderStatus.WAITING_PAYMENT);
    	
    	User user = userService.authenticated();
    	order.setClient(user);
    	
    	for (OrderItemDTO itemDto : dto.getItems()) {
    		Product product = productRepository.getReferenceById(itemDto.getProductId());
    		OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
    		order.getItems().add(item);
    	}
    	
    	repository.save(order);
    	orderItemRepository.saveAll(order.getItems());
    	
    	return new OrderDTO(order);
	}
}
