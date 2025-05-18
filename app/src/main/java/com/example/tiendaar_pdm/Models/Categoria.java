package com.example.tiendaar_pdm.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categorias")
public class Categoria {

    @PrimaryKey(autoGenerate = true)
    public int id_categoria;
    public String nombre;
    public String imagen_url;

    public Categoria(int id_categoria, String nombre, String imagen_url) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
        this.imagen_url = imagen_url;
    }

    public Categoria() {
    }

    public Categoria(String nombre, String imagen_url) {
        this.nombre = nombre;
        this.imagen_url = imagen_url;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen_url() {
        return imagen_url;
    }

    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }


    @Override
    public String toString() {
        return nombre;
    }
}
