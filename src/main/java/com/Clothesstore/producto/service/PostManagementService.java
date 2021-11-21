package com.Clothesstore.producto.service;

import com.Clothesstore.producto.dto.ProductosDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PostManagementService {

    String add(ProductosDto post) throws ExecutionException, InterruptedException;
    List<String>  addImagen(String id,MultipartFile frontal,MultipartFile trasera) throws IOException;
    ProductosDto getById(String id) ;
    List<ProductosDto> getMoreSearch();


}
