package com.devsuperior.dscommercefinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscommercefinal.entities.OrderItem;
import com.devsuperior.dscommercefinal.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
