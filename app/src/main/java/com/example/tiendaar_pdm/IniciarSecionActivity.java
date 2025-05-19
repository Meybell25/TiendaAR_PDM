package com.example.tiendaar_pdm;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendaar_pdm.DataBase.DatabaseMuebleria;
import com.example.tiendaar_pdm.Models.Usuario;
import com.example.tiendaar_pdm.VistasAdmin.MenuAdmin;
import com.example.tiendaar_pdm.VistasClientes.MenuPrincipal;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.Executors;

public class IniciarSecionActivity extends AppCompatActivity {
    private static final String TAG = "IniciarSesion";

    private TextInputEditText txtCorreo, txtPassword;
    private MaterialButton btnIniciarSesion;
    private TextView lblOlvidePassword, lblRegistrarse;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DatabaseMuebleria databaseMuebleria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_secion);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Inicializar componentes UI
        txtCorreo = findViewById(R.id.txtCorreo);
        txtPassword = findViewById(R.id.txtPassword);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        lblOlvidePassword = findViewById(R.id.lblOlvidePassword);
        lblRegistrarse = findViewById(R.id.lblRegistrarse);

        // Botón de iniciar sesión
        btnIniciarSesion.setOnClickListener(v -> iniciarSesion());

        // Olvidé mi contraseña
        lblOlvidePassword.setOnClickListener(v -> recuperarContrasena());

        // Navegar a la pantalla de registro
        lblRegistrarse.setOnClickListener(v -> {
            Intent intent = new Intent(IniciarSecionActivity.this, RegistrarActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // No realizamos verificación automática para evitar abrir los menus sin interacción
        // El inicio de sesión será explícito cuando el usuario presione el botón
    }

    private void iniciarSesion() {
        // Obtener valores de los campos
        String email = txtCorreo.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        // Validar campos
        if (TextUtils.isEmpty(email)) {
            txtCorreo.setError("Ingrese su correo electrónico");
            txtCorreo.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtCorreo.setError("Ingrese un correo electrónico válido");
            txtCorreo.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Ingrese su contraseña");
            txtPassword.requestFocus();
            return;
        }

        // Mostrar progreso o deshabilitar botón
        btnIniciarSesion.setEnabled(false);

        // Primero verificamos si el usuario existe en la base de datos local
        verificarEnBaseDatosLocal(email, password);
    }

    private void verificarEnBaseDatosLocal(String email, String password) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Inicializar la base de datos Room
                if (databaseMuebleria == null) {
                    databaseMuebleria = DatabaseMuebleria.getInstance(getApplicationContext());
                }

                // Buscar usuario por email en Room
                Usuario usuario = databaseMuebleria.usuarioDao().getUsuarioByEmail(email);

                if (usuario != null) {
                    // Usuario encontrado en Room, intentamos autenticar con Firebase
                    runOnUiThread(() -> autenticarConFirebase(email, password, usuario.getRol()));
                } else {
                    // Usuario no encontrado en Room, intentamos autenticar con Firebase sin rol predefinido
                    runOnUiThread(() -> autenticarConFirebase(email, password, null));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error al verificar usuario en la base de datos local", e);
                runOnUiThread(() -> {
                    Toast.makeText(IniciarSecionActivity.this, "Error al verificar usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    btnIniciarSesion.setEnabled(true);
                });
            }
        });
    }

    private void autenticarConFirebase(String email, String password, String rolPreExistente) {
        // Autenticar con Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inicio de sesión exitoso
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            if (rolPreExistente != null) {
                                // Ya tenemos el rol, navegamos directamente
                                navigateToMainScreen(rolPreExistente);
                            } else {
                                // Necesitamos obtener el rol desde Firestore
                                obtenerRolDesdeFirestore(user.getEmail());
                            }
                        }
                    } else {
                        // Error en el inicio de sesión
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(IniciarSecionActivity.this, "Autenticación fallida: " +
                                        (task.getException() != null ? task.getException().getMessage() : "Error desconocido"),
                                Toast.LENGTH_SHORT).show();
                        btnIniciarSesion.setEnabled(true);
                    }
                });
    }

    private void obtenerRolDesdeFirestore(String email) {
        // Mostrar progreso mientras se consulta Firestore
        Toast.makeText(this, "Verificando información de usuario...", Toast.LENGTH_SHORT).show();

        db.collection("usuarios")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        String nombre = document.getString("nombre");
                        String rol = document.getString("rol");

                        // Si el rol no está definido, asignamos uno por defecto según el email
                        if (rol == null || rol.isEmpty()) {
                            rol = com.example.tiendaar_pdm.Utils.AdminUtils.isAdminEmail(email) ? "admin" : "cliente";
                        }

                        // Guardar usuario en la base de datos local para futuras consultas
                        final String finalRol = rol;
                        guardarUsuarioEnRoomDatabase(nombre != null ? nombre : "", email, "", finalRol);

                        // Navegar a la pantalla correspondiente
                        navigateToMainScreen(finalRol);
                    } else {
                        // Usuario autenticado pero no encontrado en Firestore
                        // Asumimos que es un cliente por defecto
                        String rol = com.example.tiendaar_pdm.Utils.AdminUtils.isAdminEmail(email) ? "admin" : "cliente";
                        guardarUsuarioEnRoomDatabase("Usuario", email, "", rol);
                        navigateToMainScreen(rol);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error al obtener rol desde Firestore", e);
                    Toast.makeText(IniciarSecionActivity.this,
                            "Error al obtener información de usuario: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();

                    // En caso de error, asumimos un rol por defecto
                    String rol = com.example.tiendaar_pdm.Utils.AdminUtils.isAdminEmail(email) ? "admin" : "cliente";
                    navigateToMainScreen(rol);

                    btnIniciarSesion.setEnabled(true);
                });
    }

    private void verificarUsuarioEnBaseDatos(String email) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Inicializar la base de datos Room
                if (databaseMuebleria == null) {
                    databaseMuebleria = DatabaseMuebleria.getInstance(getApplicationContext());
                }

                // Buscar usuario por email en Room
                Usuario usuario = databaseMuebleria.usuarioDao().getUsuarioByEmail(email);

                if (usuario != null) {
                    // Usuario encontrado en la base de datos local
                    runOnUiThread(() -> navigateToMainScreen(usuario.getRol()));
                } else {
                    // Usuario no encontrado en la base de datos local, verificar en Firestore
                    verificarUsuarioEnFirestore(email);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error al verificar usuario en la base de datos local", e);
                runOnUiThread(() -> {
                    Toast.makeText(IniciarSecionActivity.this, "Error al verificar usuario en la base de datos local: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    btnIniciarSesion.setEnabled(true);
                });
            }
        });
    }

    private void verificarUsuarioEnFirestore(String email) {
        db.collection("usuarios")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        String nombre = document.getString("nombre");
                        String rol = document.getString("rol");

                        // Si el rol no está definido, verificar si es admin
                        if (rol == null || rol.isEmpty()) {
                            rol = com.example.tiendaar_pdm.Utils.AdminUtils.isAdminEmail(email) ? "admin" : "cliente";
                        }

                        // Guardar el usuario en la base de datos local
                        guardarUsuarioEnRoomDatabase(nombre != null ? nombre : "", email, "", rol);
                    } else {
                        // Usuario no encontrado en Firestore
                        Toast.makeText(IniciarSecionActivity.this, "Usuario no encontrado en la base de datos", Toast.LENGTH_SHORT).show();
                        btnIniciarSesion.setEnabled(true);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error al verificar usuario en Firestore", e);
                    Toast.makeText(IniciarSecionActivity.this, "Error al verificar usuario en Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    btnIniciarSesion.setEnabled(true);
                });
    }

    private void guardarUsuarioEnRoomDatabase(String nombre, String email, String password, String rol) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Inicializar la base de datos Room
                if (databaseMuebleria == null) {
                    databaseMuebleria = DatabaseMuebleria.getInstance(getApplicationContext());
                }

                // Crear objeto Usuario
                Usuario usuario = new Usuario(nombre, email, password, rol);

                // Verificar si el usuario ya existe para evitar duplicados
                Usuario existingUser = databaseMuebleria.usuarioDao().getUsuarioByEmail(email);

                long id;
                if (existingUser != null) {
                    // Actualizar usuario existente
                    usuario.setId_usuario(existingUser.getId_usuario());
                    databaseMuebleria.usuarioDao().updateUsuario(usuario);
                    id = existingUser.getId_usuario();
                } else {
                    // Insertar nuevo usuario
                    id = databaseMuebleria.usuarioDao().insertUsuario(usuario);
                }

                // Actualizar UI en el hilo principal
                final long finalId = id;
                runOnUiThread(() -> {
                    if (finalId > 0) {
                        // Mostrar mensaje de éxito
                        Toast.makeText(IniciarSecionActivity.this, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show();
                        // Navegar a la pantalla principal según el rol
                        navigateToMainScreen(rol);
                    } else {
                        Toast.makeText(IniciarSecionActivity.this, "Error al guardar en la base de datos local", Toast.LENGTH_SHORT).show();
                        btnIniciarSesion.setEnabled(true);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error al guardar en la base de datos Room", e);
                runOnUiThread(() -> {
                    Toast.makeText(IniciarSecionActivity.this, "Error al guardar en la base de datos local: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    btnIniciarSesion.setEnabled(true);
                });
            }
        });
    }

    private void recuperarContrasena() {
        String email = txtCorreo.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            txtCorreo.setError("Ingrese su correo electrónico para recuperar su contraseña");
            txtCorreo.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtCorreo.setError("Ingrese un correo electrónico válido");
            txtCorreo.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(IniciarSecionActivity.this, "Se ha enviado un correo para restablecer su contraseña", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(IniciarSecionActivity.this, "Error al enviar el correo de recuperación: " +
                                        (task.getException() != null ? task.getException().getMessage() : "Error desconocido"),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void navigateToMainScreen(String rol) {
        Intent intent;

        // Determinar qué actividad abrir según el rol del usuario
        if ("admin".equalsIgnoreCase(rol)) {
            intent = new Intent(IniciarSecionActivity.this, MenuAdmin.class);
            Log.d(TAG, "Navegando al menú de administrador");
        } else {
            intent = new Intent(IniciarSecionActivity.this, MenuPrincipal.class);
            Log.d(TAG, "Navegando al menú de cliente");
        }

        // Agregar información extra al intent si es necesario
        intent.putExtra("email", txtCorreo.getText().toString().trim());

        // Limpiar la pila de actividades para que el usuario no pueda volver
        // a la pantalla de inicio de sesión presionando el botón Atrás
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish(); // Cerramos esta actividad
    }
}