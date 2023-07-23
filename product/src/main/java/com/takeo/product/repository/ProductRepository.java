package com.takeo.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takeo.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}