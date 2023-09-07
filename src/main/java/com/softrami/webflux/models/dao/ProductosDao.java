package com.softrami.webflux.models.dao;

import com.softrami.webflux.models.documents.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductosDao extends ReactiveMongoRepository<Product, String> {
}
