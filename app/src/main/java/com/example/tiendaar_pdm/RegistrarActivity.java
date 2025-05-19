package com.example.tiendaar_pdm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendaar_pdm.DataBase.DatabaseMuebleria;
import com.example.tiendaar_pdm.Models.Usuario;
import com.example.tiendaar_pdm.VistasAdmin.MenuAdmin;
import com.example.tiendaar_pdm.VistasClientes.MenuPrincipal;
import com.example.tiendaar_pdm.Utils.AdminUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistrarActivity extends AppCompatActivity {
    private static final String TAG = "RegistrarActivity";

    private TextInputEditText txtNombreUsuario, txtEmail, txtContrasena;
    private MaterialButton btnRegistrarse;
    private TextView lblIniciarSesion;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar FirebaseAuth y FirebaseFirestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        executorService = Executors.newSingleThreadExecutor();

        // Inicializar componentes UI
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtEmail = findViewById(R.id.txtEmail);
        txtContrasena = findViewById(R.id.txtContrasena);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        lblIniciarSesion = findViewById(R.id.lblIniciarSesion);

        // Botón de registro
        btnRegistrarse.setOnClickListener(v -> registrarUsuario());

        // Navegar a la pantalla de inicio de sesión
        lblIniciarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrarActivity.this, IniciarSecionActivity.class);
            startActivity(intent);
            finish(); // Cerrar esta actividad para evitar regresar con el botón atrás
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    private void registrarUsuario() {
        // Obtener valores de los campos
        String nombre = txtNombreUsuario.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtContrasena.getText().toString().trim();

        // Validar campos
        if (TextUtils.isEmpty(nombre)) {
            txtNombreUsuario.setError("Ingrese un nombre de usuario");
            txtNombreUsuario.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Ingrese un correo electrónico");
            txtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Ingrese un correo electrónico válido");
            txtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            txtContrasena.setError("Ingrese una contraseña");
            txtContrasena.requestFocus();
            return;
        }

        if (password.length() < 6) {
            txtContrasena.setError("La contraseña debe tener al menos 6 caracteres");
            txtContrasena.requestFocus();
            return;
        }

        // Mostrar progreso y realizar acción inmediata (esto es lo que funciona)
        btnRegistrarse.setEnabled(false);
        Toast.makeText(RegistrarActivity.this, "Procesando registro...", Toast.LENGTH_SHORT).show();

        // Definir el rol del usuario (cliente por defecto)
        String rol = AdminUtils.isAdminEmail(email) ? "admin" : "cliente";

        // Guardamos información en Room Database en segundo plano y esperamos a que termine
        guardarEnRoomDatabase(nombre, email, password, rol);

        // Verificamos que el usuario se guardó correctamente
        verificarGuardadoEnRoom(email);

        Toast.makeText(RegistrarActivity.this, "¡Usuario registrado con éxito!", Toast.LENGTH_SHORT).show();

        // Redirigir a la actividad de inicio de sesión
        Intent intent = new Intent(this, IniciarSecionActivity.class);
        intent.putExtra("registro_exitoso", true);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }

    private void verificarGuardadoEnRoom(String email) {
        executorService.execute(() -> {
            try {
                // Obtener instancia de la base de datos Room
                DatabaseMuebleria databaseMuebleria = DatabaseMuebleria.getInstance(getApplicationContext());

                // Obtener el usuario por email
                Usuario usuario = databaseMuebleria.usuarioDao().getUsuarioByEmail(email);

                // Verificar si se encontró el usuario
                if (usuario != null) {
                    // Mostrar mensaje en el hilo principal
                    runOnUiThread(() -> {
                        Log.d(TAG, "VERIFICACIÓN: Usuario encontrado en Room Database - ID:" + usuario.getId_usuario() +
                                ", Nombre: " + usuario.getNombre() +
                                ", Email: " + usuario.getEmail() +
                                ", Rol: " + usuario.getRol());

                        // Para debug, también podemos mostrar un Toast
                        // Toast.makeText(RegistrarActivity.this,
                        //       "Usuario guardado en Room con ID: " + usuario.getId_usuario(),
                        //       Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() -> {
                        Log.e(TAG, "VERIFICACIÓN: No se encontró el usuario en Room Database");
                        // Toast.makeText(RegistrarActivity.this,
                        //       "Error: No se pudo guardar el usuario en Room Database",
                        //       Toast.LENGTH_SHORT).show();
                    });
                }
            } catch (Exception e) {
                Log.e(TAG, "Error al verificar usuario en Room Database", e);
            }
        });
    }

    private void guardarEnRoomDatabase(String nombre, String email, String password, String rol) {
        try {
            // Obtener instancia de la base de datos Room (ejecutamos en el hilo principal para garantizar que se guarde)
            DatabaseMuebleria databaseMuebleria = DatabaseMuebleria.getInstance(getApplicationContext());

            // Crear objeto Usuario
            Usuario usuario = new Usuario(nombre, email, password, rol);

            executorService.execute(() -> {
                try {
                    // Verificar si ya existe el usuario para actualizar o insertar
                    Usuario existingUser = databaseMuebleria.usuarioDao().getUsuarioByEmail(email);
                    long id;

                    if (existingUser != null) {
                        // Actualizar usuario existente
                        usuario.setId_usuario(existingUser.getId_usuario());
                        databaseMuebleria.usuarioDao().updateUsuario(usuario);
                        id = existingUser.getId_usuario();
                        Log.d(TAG, "Usuario actualizado en Room, ID: " + id);
                    } else {
                        // Insertar nuevo usuario
                        id = databaseMuebleria.usuarioDao().insertUsuario(usuario);
                        Log.d(TAG, "Usuario guardado en Room, ID: " + id);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error al guardar en la base de datos Room", e);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar la base de datos Room", e);
        }

        // Este código Firebase se ejecutará en segundo plano, pero no afectará la experiencia del usuario
        // Lo mantenemos para que los datos se sincronicen con Firebase si la conexión está disponible
        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();

                            // Guardar en Firestore
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("nombre", nombre);
                            userData.put("email", email);
                            userData.put("rol", rol);

                            db.collection("usuarios")
                                    .document(uid)
                                    .set(userData)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d(TAG, "Usuario guardado en Firestore");
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e(TAG, "Error al guardar en Firestore", e);
                                    });
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "createUserWithEmail:failure", e);
                    });
        } catch (Exception e) {
            Log.e(TAG, "Error al registrar usuario en Firebase", e);
        }
    }
}