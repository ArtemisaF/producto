package com.Clothesstore.producto;

import com.Clothesstore.producto.controller.PostController;
import com.Clothesstore.producto.dto.ProductosDto;
import com.Clothesstore.producto.service.PostManagementService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProductoUnitTests {

    @Mock
    private PostManagementService service;

    @Mock
    private ProductosDto dto;

    @Mock
    private List<ProductosDto> dtoList;

    @InjectMocks
    private PostController controller;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUsingId(){

        String id ="4ueFE4RAIVATCoe1TURk";

        dto=new ProductosDto();
        dto.setNombre("Medias largas");
        dto.setDescripcion("Son azules tipo tobillera, antideslisante");
        dto.setPrecio(5000);
        dto.setPorcentajeDescuento(10);
        List<String> Imagen= new ArrayList<>();
        Imagen.add("1637449253838-526887.jpg");
        Imagen.add("1637449261403-Captura.PNG");
        dto.setImagen(Imagen);
        dto.setPais("Peru");
        dto.setNumeroDeBusquedas(4);

        Mockito.when(service.getById(id)).thenReturn(dto);

    }

    @Test
    public void CorrectMessageWhenTheDiscountPercentageInColombiaIsIncorrect() throws ExecutionException, InterruptedException {
        String PaisUpper="COLOMBIA";
        String PaisLower="Colombia";
        Integer PorceentajeDescuento=61;


        dto=new ProductosDto();
        dto.setPais(PaisUpper);
        dto.setPorcentajeDescuento(PorceentajeDescuento);

        Mockito.when(service.add(dto)).thenReturn("No debe superar el 50% en estos paises(Colombia, Mexico)");
        dto.setPais(PaisLower);
        Mockito.when(service.add(dto)).thenReturn("No debe superar el 50% en estos paises(Colombia, Mexico)");
    }

    @Test
    public void CorrectMessageWhenTheDiscountPercentageInMexicoIsIncorrect() throws ExecutionException, InterruptedException {
        String PaisUpper="MEXICO";
        String PaisLower="mexico";
        Integer PorceentajeDescuento=59;

        dto=new ProductosDto();
        dto.setPais(PaisUpper);
        dto.setPorcentajeDescuento(PorceentajeDescuento);

        Mockito.when(service.add(dto)).thenReturn("No debe superar el 50% en estos paises(Colombia, Mexico)");
        dto.setPais(PaisLower);
        Mockito.when(service.add(dto)).thenReturn("No debe superar el 50% en estos paises(Colombia, Mexico)");
    }

    @Test
    public void CorrectMessageWhenTheDiscountPercentageInChileIsIncorrect() throws ExecutionException, InterruptedException {
        String PaisUpper="CHILE";
        String PaisLower="chile";
        Integer PorceentajeDescuento=45;


        dto=new ProductosDto();
        dto.setPais(PaisUpper);
        dto.setPorcentajeDescuento(PorceentajeDescuento);

        Mockito.when(service.add(dto)).thenReturn("No debe superar el 30% en estos paises(Chile, Peru)");
        dto.setPais(PaisLower);
        Mockito.when(service.add(dto)).thenReturn("No debe superar el 30% en estos paises(Chile, Peru)");
    }

    @Test
    public void CorrectMessageWhenTheDiscountPercentageInPeruIsIncorrect() throws ExecutionException, InterruptedException {
        String PaisUpper="PERU";
        String PaisLower="peru";
        Integer PorceentajeDescuento=31;


        dto=new ProductosDto();
        dto.setPais(PaisUpper);
        dto.setPorcentajeDescuento(PorceentajeDescuento);

        Mockito.when(service.add(dto)).thenReturn("No debe superar el 30% en estos paises(Chile, Peru)");
        dto.setPais(PaisLower);
        Mockito.when(service.add(dto)).thenReturn("No debe superar el 30% en estos paises(Chile, Peru)");
    }


    @Test
    public void getMoreSearch(){

        dtoList=new ArrayList<>();

        dto=new ProductosDto();
        dto.setNombre("Zapatos");
        dto.setDescripcion("Son rojos");
        dto.setPrecio(100000);
        dto.setPorcentajeDescuento(15);
        List<String> Imagen= new ArrayList<>();
        Imagen.add("1637439924164-22.jpg");
        Imagen.add("1637439925445-526887.jpg");
        dto.setImagen(Imagen);
        dto.setPais("Colombia");
        dto.setNumeroDeBusquedas(8);
        dtoList.add(dto);

        dto=new ProductosDto();
        dto.setNombre("Medias largas");
        dto.setDescripcion("Son azules tipo tobillera, antideslisante");
        dto.setPrecio(5000);
        dto.setPorcentajeDescuento(10);
        List<String> Imagen2= new ArrayList<>();
        Imagen.add("1637449253838-526887.jpg");
        Imagen.add("1637449261403-Captura.PNG");
        dto.setImagen(Imagen2);
        dto.setPais("Peru");
        dto.setNumeroDeBusquedas(4);
        dtoList.add(dto);

        Mockito.when(service.getMoreSearch()).thenReturn(dtoList);

    }
}
