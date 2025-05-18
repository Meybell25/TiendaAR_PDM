package com.example.tiendaar_pdm.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tiendaar_pdm.Daos.CategoriaDao;
import com.example.tiendaar_pdm.Daos.CarritoDao;
import com.example.tiendaar_pdm.Daos.CarritoDetalleDao;
import com.example.tiendaar_pdm.Daos.OrdenDao;
import com.example.tiendaar_pdm.Daos.OrdenDetalleDao;
import com.example.tiendaar_pdm.Daos.ProductoDao;
import com.example.tiendaar_pdm.Daos.UsuarioDao;

import com.example.tiendaar_pdm.Models.Categoria;
import com.example.tiendaar_pdm.Models.Carrito;
import com.example.tiendaar_pdm.Models.CarritoDetalle;
import com.example.tiendaar_pdm.Models.Orden;
import com.example.tiendaar_pdm.Models.OrdenDetalle;
import com.example.tiendaar_pdm.Models.Producto;
import com.example.tiendaar_pdm.Models.Usuario;

@Database(
        entities = {
                Usuario.class,
                Categoria.class,
                Producto.class,
                Carrito.class,
                CarritoDetalle.class,
                Orden.class,
                OrdenDetalle.class
        },
        version = 1,
        exportSchema = false
)
public abstract class DatabaseMuebleria extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();
    public abstract CategoriaDao categoriaDao();
    public abstract ProductoDao productoDao();
    public abstract CarritoDao carritoDao();
    public abstract CarritoDetalleDao carritoDetalleDao();
    public abstract OrdenDao ordenDao();
    public abstract OrdenDetalleDao ordenDetalleDao();

    private static  DatabaseMuebleria INSTANCE;

    public static synchronized DatabaseMuebleria getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DatabaseMuebleria.class,
                            "MuebleriaDB"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
