package com.example.tiendaar_pdm.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "ordenes",
        foreignKeys = {
                @ForeignKey(
                        entity = Usuario.class,
                        parentColumns = "id_usuario",
                        childColumns = "id_usuario",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index(value = "id_usuario")}
)
public class Orden {

    @PrimaryKey(autoGenerate = true)
    public int id_orden;

    @ColumnInfo(name = "id_usuario")
    public int id_usuario;

    public String fecha;

    public double total;

    public String estado; // Ejemplo: "pendiente", "enviado", "cancelado"


    public Orden(int id_orden, int id_usuario, String fecha, double total, String estado) {
        this.id_orden = id_orden;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    public Orden() {
    }

    public Orden(int id_usuario, String fecha, double total, String estado) {
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Orden{" +
                "id_orden=" + id_orden +
                ", id_usuario=" + id_usuario +
                ", fecha='" + fecha + '\'' +
                ", total=" + total +
                ", estado='" + estado + '\'' +
                '}';
    }
}
