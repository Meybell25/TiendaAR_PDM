package com.example.tiendaar_pdm.Utils;

import java.util.Arrays;
import java.util.List;

public class AdminUtils {

    // Lista de correos electrónicos de administradores
    private static final List<String> ADMIN_EMAILS = Arrays.asList(
            "rq22004@ues.edu.sv",
            "administrador@tiendaar.com"
            //  agregar más correos de administradores aquí
    );

    /**
     * Verifica si un correo electrónico pertenece a un administrador
     * @param email Correo electrónico a verificar
     * @return true si es un correo de administrador, false en caso contrario
     */
    public static boolean isAdminEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        // Verificar si el correo está en la lista de administradores
        return ADMIN_EMAILS.contains(email.toLowerCase().trim());
    }
}