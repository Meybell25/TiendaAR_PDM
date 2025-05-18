package com.example.tiendaar_pdm.POJO;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tiendaar_pdm.Models.Carrito;
import com.example.tiendaar_pdm.Models.CarritoDetalle;

import java.util.List;

public class CarritoConDetalles {

    @Embedded
    public Carrito carrito;

    @Relation(
            parentColumn = "id_carrito",
            entityColumn = "id_carrito"
    )
    public List<CarritoDetalle> detalles;
}
