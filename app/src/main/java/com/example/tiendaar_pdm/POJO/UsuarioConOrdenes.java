package com.example.tiendaar_pdm.POJO;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tiendaar_pdm.Models.Usuario;
import com.example.tiendaar_pdm.Models.Orden;

import java.util.List;

public class UsuarioConOrdenes {

    @Embedded
    public Usuario usuario;

    @Relation(
            parentColumn = "id_usuario",
            entityColumn = "id_usuario"
    )
    public List<Orden> ordenes;
}
