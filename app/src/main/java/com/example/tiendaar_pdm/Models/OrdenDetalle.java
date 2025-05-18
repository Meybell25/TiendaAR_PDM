package com.example.tiendaar_pdm.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "orden_detalles",
        foreignKeys = {
                @ForeignKey(
                        entity = Orden.class,
                        parentColumns = "id_orden",
                        childColumns = "id_orden",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = Producto.class,
                        parentColumns = "id_producto",
                        childColumns = "id_producto",
                        onDelete = ForeignKey.RESTRICT)
        },
        indices = {@Index(value = "id_orden"), @Index(value = "id_producto")}
)
public class OrdenDetalle {

    @PrimaryKey(autoGenerate = true)
    public int id_orden_det;

    @ColumnInfo(name = "id_orden")
    public int id_orden;

    @ColumnInfo(name = "id_producto")
    public int id_producto;

    public int cantidad;

    @ColumnInfo(name = "precio_unitario")
    public double precio_unitario;

    public OrdenDetalle(int id_orden_det, int id_orden, int id_producto, int cantidad, double precio_unitario) {
        this.id_orden_det = id_orden_det;
        this.id_orden = id_orden;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    public OrdenDetalle() {
    }

    public OrdenDetalle(int id_orden, int id_producto, int cantidad, double precio_unitario) {
        this.id_orden = id_orden;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    public int getId_orden_det() {
        return id_orden_det;
    }

    public void setId_orden_det(int id_orden_det) {
        this.id_orden_det = id_orden_det;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
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

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    @Override
    public String toString() {
        return "OrdenDetalle{" +
                "id_orden_det=" + id_orden_det +
                ", id_orden=" + id_orden +
                ", id_producto=" + id_producto +
                ", cantidad=" + cantidad +
                ", precio_unitario=" + precio_unitario +
                '}';
    }
}
