package com.example.tiendaar_pdm.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.tiendaar_pdm.Models.Orden;
import com.example.tiendaar_pdm.POJO.OrdenConDetalles;

import java.util.List;

@Dao
public interface OrdenDao {

    @Insert
    long insertOrden(Orden orden);

    @Update
    void updateOrden(Orden orden);

    @Delete
    void deleteOrden(Orden orden);

    @Query("SELECT * FROM ordenes WHERE id_orden = :id")
    Orden getOrdenById(int id);

    @Query("SELECT * FROM ordenes WHERE id_usuario = :usuarioId")
    List<Orden> getOrdenesByUsuario(int usuarioId);

    @Query("SELECT * FROM ordenes")
    List<Orden> getAllOrdenes();

    @Transaction
    @Query("SELECT * FROM ordenes WHERE id_orden = :id")
    OrdenConDetalles getOrdenConDetalles(int id);

    @Transaction
    @Query("SELECT * FROM ordenes WHERE id_usuario = :idUsuario")
    List<OrdenConDetalles> getOrdenesPorUsuario(int idUsuario);
}
