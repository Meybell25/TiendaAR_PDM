package com.example.tiendaar_pdm.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "productos",
        foreignKeys = @ForeignKey(
                entity = Categoria.class,
                parentColumns = "id_categoria",
                childColumns = "categoria_id",
                onDelete = ForeignKey.RESTRICT),
        indices = {@Index(value = "categoria_id")}
)
public class Producto {

    @PrimaryKey(autoGenerate = true)
    public int id_producto;

    public String nombre;
    public String descripcion;
    public double precio;
    public int stock;
    public int categoria_id;
    public String marca;
    public String modelo_3d_url;
    public String imagen_url;
    public boolean activo;


    public Producto(int id_producto, String nombre, String descripcion, double precio, int stock, int categoria_id, String marca, String modelo_3d_url, String imagen_url, boolean activo) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria_id = categoria_id;
        this.marca = marca;
        this.modelo_3d_url = modelo_3d_url;
        this.imagen_url = imagen_url;
        this.activo = activo;
    }

    public Producto() {
    }

    public Producto(String nombre, String descripcion, double precio, int stock, int categoria_id, String marca, String modelo_3d_url, String imagen_url, boolean activo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria_id = categoria_id;
        this.marca = marca;
        this.modelo_3d_url = modelo_3d_url;
        this.imagen_url = imagen_url;
        this.activo = activo;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo_3d_url() {
        return modelo_3d_url;
    }

    public void setModelo_3d_url(String modelo_3d_url) {
        this.modelo_3d_url = modelo_3d_url;
    }

    public String getImagen_url() {
        return imagen_url;
    }

    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
