package com.example.tiendaar_pdm.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "carrito_detalles",
        foreignKeys = {
                @ForeignKey(
                        entity = Carrito.class,
                        parentColumns = "id_carrito",
                        childColumns = "id_carrito",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = Producto.class,
                        parentColumns = "id_producto",
                        childColumns = "id_producto",
                        onDelete = ForeignKey.RESTRICT)
        },
        indices = {@Index(value = "id_carrito"), @Index(value = "id_producto")}
)
public class CarritoDetalle {

    @PrimaryKey(autoGenerate = true)
    public int id_carrito_det;

    @ColumnInfo(name = "id_carrito")
    public int id_carrito;

    @ColumnInfo(name = "id_producto")
    public int id_producto;

    public int cantidad;

    public CarritoDetalle(int id_carrito_det, int id_carrito, int id_producto, int cantidad) {
        this.id_carrito_det = id_carrito_det;
        this.id_carrito = id_carrito;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
    }

    public CarritoDetalle() {
    }

    public CarritoDetalle(int id_carrito, int id_producto, int cantidad) {
        this.id_carrito = id_carrito;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
    }

    public int getId_carrito_det() {
        return id_carrito_det;
    }

    public void setId_carrito_det(int id_carrito_det) {
        this.id_carrito_det = id_carrito_det;
    }

    public int getId_carrito() {
        return id_carrito;
    }

    public void setId_carrito(int id_carrito) {
        this.id_carrito = id_carrito;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "CarritoDetalle{" +
                "id_carrito_det=" + id_carrito_det +
                ", id_carrito=" + id_carrito +
                ", id_producto=" + id_producto +
                ", cantidad=" + cantidad +
                '}';
    }
}
