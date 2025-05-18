package com.example.tiendaar_pdm.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.tiendaar_pdm.Models.CarritoDetalle;

import java.util.List;

@Dao
public interface CarritoDetalleDao {

    @Insert
    long insertCarritoDetalle(CarritoDetalle carritoDetalle);

    @Update
    void updateCarritoDetalle(CarritoDetalle carritoDetalle);

    @Delete
    void deleteCarritoDetalle(CarritoDetalle carritoDetalle);

    @Query("SELECT * FROM carrito_detalles WHERE id_carrito_det = :id")
    CarritoDetalle getCarritoDetalleById(int id);

    @Query("SELECT * FROM carrito_detalles WHERE id_carrito = :carritoId")
    List<CarritoDetalle> getDetallesByCarrito(int carritoId);

    @Query("SELECT * FROM carrito_detalles")
    List<CarritoDetalle> getAllCarritoDetalles();
}
