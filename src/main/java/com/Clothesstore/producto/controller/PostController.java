package com.Clothesstore.producto.controller;

import com.Clothesstore.producto.dto.ProductosDto;
import com.Clothesstore.producto.service.PostManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RestController
public class PostController {

    @Autowired
    private PostManagementService service;

    @RequestMapping(value = "producto",method = RequestMethod.POST)
    public ResponseEntity addProducto(@RequestBody ProductosDto post) throws ExecutionException, InterruptedException {
        return new ResponseEntity(service.add(post), HttpStatus.OK);

    }

    @RequestMapping(value = "producto/updateImage/{id}",method = RequestMethod.POST)
    public ResponseEntity addImagen(@PathVariable(value = "id")String id,@RequestParam("file") MultipartFile file,@RequestParam("file2") MultipartFile file2) throws IOException {
        return new ResponseEntity(service.addImagen(id,file,file2), HttpStatus.OK);

    }

    @RequestMapping(value = "getProducto/{id}",method = RequestMethod.GET)
    public ResponseEntity getProductoById(@PathVariable(value = "id")String id) {
        return  new ResponseEntity(service.getById(id),HttpStatus.OK);
    }

    @RequestMapping(value = "getMoreSearch",method = RequestMethod.GET)
    public ResponseEntity getMoreSearch(){
        return  new ResponseEntity(service.getMoreSearch(),HttpStatus.OK);
    }


}

