package com.devsuperior.dscommercefinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dscommercefinal.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
