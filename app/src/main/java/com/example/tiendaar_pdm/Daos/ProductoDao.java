package com.example.tiendaar_pdm.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.tiendaar_pdm.Models.Producto;
import com.example.tiendaar_pdm.POJO.ProductoConCategoria;

import java.util.List;

@Dao
public interface ProductoDao {

    @Insert
    long insertProducto(Producto producto);

    @Update
    int updateProducto(Producto producto);

    @Delete
    int deleteProducto(Producto producto);

    @Query("SELECT * FROM productos WHERE id_producto = :id")
    Producto getProductoById(int id);

    @Query("SELECT * FROM productos WHERE activo = 1")
    List<Producto> getProductosActivos();

    @Query("SELECT * FROM productos WHERE categoria_id = :categoriaId AND activo = 1")
    List<Producto> getProductosPorCategoria(int categoriaId);

    @Query("SELECT * FROM productos")
    List<Producto> getAllProductos();

    @Transaction
    @Query("SELECT * FROM productos WHERE id_producto = :id")
    ProductoConCategoria getProductoConCategoria(int id);

    @Transaction
    @Query("SELECT * FROM productos")
    List<ProductoConCategoria> getTodosProductosConCategoria();
}
