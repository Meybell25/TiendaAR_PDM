package com.example.tiendaar_pdm.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "carrito",
        foreignKeys = @ForeignKey(
                entity = Usuario.class,
                parentColumns = "id_usuario",
                childColumns = "id_usuario",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "id_usuario")}
)
public class Carrito {

    @PrimaryKey(autoGenerate = true)
    public int id_carrito;

    @ColumnInfo(name = "id_usuario")
    public int id_usuario;

    public String fecha;

    public Carrito(int id_carrito, int id_usuario, String fecha) {
        this.id_carrito = id_carrito;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
    }

    public Carrito() {
    }

    public Carrito(int id_usuario, String fecha) {
        this.id_usuario = id_usuario;
        this.fecha = fecha;
    }

    public int getId_carrito() {
        return id_carrito;
    }

    public void setId_carrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
