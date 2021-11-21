package com.Clothesstore.producto.dto;


import java.util.List;

public class ProductosDto {

    private String Nombre;
    private String Descripcion;
    private Integer Precio;
    private Integer PorcentajeDescuento;
    private List<String> Imagen;
    private String Pais;
    private Integer NumeroDeBusquedas;

    public Integer getNumeroDeBusquedas() {
        return NumeroDeBusquedas;
    }

    public void setNumeroDeBusquedas(Integer numeroDeBusquedas) {
        NumeroDeBusquedas = numeroDeBusquedas;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public void setPrecio(Integer precio) {
        Precio = precio;
    }

    public void setPorcentajeDescuento(Integer porcentajeDescuento) {
        PorcentajeDescuento = porcentajeDescuento;
    }

    public void setImagen(List<String> imagen) {
        Imagen = imagen;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public Integer getPrecio() {
        return Precio;
    }

    public Integer getPorcentajeDescuento() {
        return PorcentajeDescuento;
    }

    public List<String> getImagen() {
        return Imagen;
    }

    public String getPais() {
        return Pais;
    }


}
