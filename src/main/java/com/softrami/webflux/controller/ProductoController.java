package com.softrami.webflux.controller;

import com.softrami.webflux.WebfluxApplication;
import com.softrami.webflux.models.dao.ProductosDao;
import com.softrami.webflux.models.documents.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class ProductoController {

    private static final Logger log= LoggerFactory.getLogger(WebfluxApplication.class);
    private final ProductosDao productosDao;

    public ProductoController(ProductosDao productosDao) {
        this.productosDao = productosDao;
    }


    @GetMapping({"/listar","/"})
    public String listar(Model model){
            Flux<Product> productos= productosDao.findAll().map( producto -> {
                producto.setNombre(producto.getNombre().toUpperCase());
                return producto;
            });
            productos.subscribe(prod-> log.info(prod.getNombre()));

            model.addAttribute("productos",productos);
            model.addAttribute("titulo","Listado de productos");
            return "listar";
        }
    // Forma de manejar contrapresion utilizando thymeleaf que permite configurar la cantidad de elementos
    // en el buffer
    @GetMapping("listar-datadriver")
    public String listarDataDriver(Model model){
        Flux<Product> productos= productosDao.findAll().map( producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        }).delayElements(Duration.ofSeconds(1));
        productos.subscribe(prod-> log.info(prod.getNombre()));

        model.addAttribute("productos",new ReactiveDataDriverContextVariable(productos,2) );
        model.addAttribute("titulo","Listado de productos");

        return "listar";
    }

    @GetMapping("/listar-todo")
    public String listarTodo(Model model){
        Flux<Product> productos= productosDao.findAll().map( producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        }).repeat(2000);
        model.addAttribute("productos",productos);
        model.addAttribute("titulo","Listado de productos");

        return "listar";
    }

    @GetMapping("/listar-chunked")
    public String listarChunked(Model model){
        Flux<Product> productos= productosDao.findAll().map( producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        }).repeat(2000);

        model.addAttribute("productos",productos);
        model.addAttribute("titulo","Listado de productos");

        return "listar-chunked";

    }

}
