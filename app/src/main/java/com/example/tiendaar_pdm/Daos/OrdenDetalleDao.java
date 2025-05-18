package com.example.tiendaar_pdm.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.tiendaar_pdm.Models.OrdenDetalle;

import java.util.List;

@Dao
public interface OrdenDetalleDao {

    @Insert
    long insertOrdenDetalle(OrdenDetalle ordenDetalle);

    @Update
    void updateOrdenDetalle(OrdenDetalle ordenDetalle);

    @Delete
    void deleteOrdenDetalle(OrdenDetalle ordenDetalle);

    @Query("SELECT * FROM orden_detalles WHERE id_orden_det = :id")
    OrdenDetalle getOrdenDetalleById(int id);

    @Query("SELECT * FROM orden_detalles WHERE id_orden = :ordenId")
    List<OrdenDetalle> getDetallesByOrden(int ordenId);

    @Query("SELECT * FROM orden_detalles")
    List<OrdenDetalle> getAllOrdenDetalles();
}
