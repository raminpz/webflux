package com.softrami.webflux.controller;

import com.softrami.webflux.models.dao.ProductosDao;
import com.softrami.webflux.models.documents.Product;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/productos")
public class ProductoRestController {

    private final ProductosDao productosDao;

    public ProductoRestController(ProductosDao productosDao) {
        this.productosDao = productosDao;
    }
    @GetMapping("/{id}")
    public Mono<Product> mostrarProductoId(@PathVariable String id){
        Mono<Product> productMono = productosDao.findById(id);

        return productMono;
    }

    @GetMapping("/listar")
    public Flux<Product> mostrarTodo(){
        Flux<Product> productFlux = productosDao.findAll();
        return productFlux;
    }


}
