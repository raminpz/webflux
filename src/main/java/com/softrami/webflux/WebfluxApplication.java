package com.softrami.webflux;

import com.softrami.webflux.models.dao.ProductosDao;
import com.softrami.webflux.models.documents.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class WebfluxApplication implements CommandLineRunner {

    private final ProductosDao productosDao;

    private static final Logger log= LoggerFactory.getLogger(WebfluxApplication.class);

    public WebfluxApplication(ProductosDao productosDao) {
        this.productosDao = productosDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        Flux.just(new Product("Computadora", 983.09),
                new Product("Laptop", 983.09),
        new Product("Parlante", 983.09),
        new Product("Guitarra", 983.09),
        new Product("Celular", 983.09))
                .flatMap(p -> {
                    p.setFechaCreacion(new Date());
                    return productosDao.save(p);
                }).subscribe(product -> log.info("Producto insertado: "+product.getId()+" "+ product.getNombre()));

    }
}
