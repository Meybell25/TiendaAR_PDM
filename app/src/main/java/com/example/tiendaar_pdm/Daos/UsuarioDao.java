package com.example.tiendaar_pdm.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.tiendaar_pdm.Models.Usuario;
import com.example.tiendaar_pdm.POJO.UsuarioConCarritos;
import com.example.tiendaar_pdm.POJO.UsuarioConOrdenes;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    long insertUsuario(Usuario usuario);

    @Update
    int updateUsuario(Usuario usuario);

    @Delete
    int deleteUsuario(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE id_usuario = :id")
    Usuario getUsuarioById(int id);

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    Usuario getUsuarioByEmail(String email);

    @Query("SELECT * FROM usuarios")
    List<Usuario> getAllUsuarios();

    @Transaction
    @Query("SELECT * FROM usuarios WHERE id_usuario = :id")
    UsuarioConCarritos getUsuarioConCarritos(int id);

    @Transaction
    @Query("SELECT * FROM usuarios")
    List<UsuarioConCarritos> getTodosUsuariosConCarritos();

    @Transaction
    @Query("SELECT * FROM usuarios WHERE id_usuario = :id")
    UsuarioConOrdenes getUsuarioConOrdenes(int id);

    @Transaction
    @Query("SELECT * FROM usuarios")
    List<UsuarioConOrdenes> getTodosUsuariosConOrdenes();
}
