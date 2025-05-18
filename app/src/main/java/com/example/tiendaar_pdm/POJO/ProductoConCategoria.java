package com.example.tiendaar_pdm.POJO;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tiendaar_pdm.Models.Producto;
import com.example.tiendaar_pdm.Models.Categoria;

public class ProductoConCategoria {

    @Embedded
    public Producto producto;

    @Relation(
            parentColumn = "categoria_id",
            entityColumn = "id_categoria"
    )
    public Categoria categoria;
}
