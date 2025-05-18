package com.example.tiendaar_pdm.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.tiendaar_pdm.Models.Carrito;
import com.example.tiendaar_pdm.POJO.CarritoConDetalles;

import java.util.List;

@Dao
public interface CarritoDao {

    @Insert
    long insertCarrito(Carrito carrito);

    @Update
    void updateCarrito(Carrito carrito);

    @Delete
    void deleteCarrito(Carrito carrito);

    @Query("SELECT * FROM carrito WHERE id_carrito = :id")
    Carrito getCarritoById(int id);

    @Query("SELECT * FROM carrito WHERE id_usuario = :userId")
    List<Carrito> getCarritosByUsuario(int userId);

    @Query("SELECT * FROM carrito")
    List<Carrito> getAllCarritos();

    @Transaction
    @Query("SELECT * FROM carrito WHERE id_carrito = :id")
    CarritoConDetalles getCarritoConDetalles(int id);

    @Transaction
    @Query("SELECT * FROM carrito WHERE id_usuario = :idUsuario")
    List<CarritoConDetalles> getCarritosPorUsuario(int idUsuario);
}
