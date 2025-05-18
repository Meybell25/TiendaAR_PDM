package com.example.tiendaar_pdm.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.tiendaar_pdm.Models.Categoria;

import java.util.List;

@Dao
public interface CategoriaDao {

    @Insert
    long insertCategoria(Categoria categoria);

    @Update
    int updateCategoria(Categoria categoria);

    @Delete
    int deleteCategoria(Categoria categoria);

    @Query("SELECT * FROM categorias WHERE id_categoria = :id")
    Categoria getCategoriaById(int id);

    @Query("SELECT * FROM categorias")
    List<Categoria> getAllCategorias();
}
