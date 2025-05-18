package com.example.tiendaar_pdm.POJO;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tiendaar_pdm.Models.Orden;
import com.example.tiendaar_pdm.Models.OrdenDetalle;

import java.util.List;

public class OrdenConDetalles {

    @Embedded
    public Orden orden;

    @Relation(
            parentColumn = "id_orden",
            entityColumn = "id_orden"
    )
    public List<OrdenDetalle> detalles;
}
