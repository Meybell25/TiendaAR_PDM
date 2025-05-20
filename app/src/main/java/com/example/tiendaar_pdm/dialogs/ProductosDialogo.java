package com.example.tiendaar_pdm.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tiendaar_pdm.R;
import com.google.android.material.button.MaterialButton;

public class ProductosDialogo extends DialogFragment {

    private EditText txtNombreProducto, txtDescripcionProducto, txtPrecioProducto, txtStockProducto, txtMarcaProducto, txtModelo3D;
    private Spinner spinnerCategoria;
    private MaterialButton btnInsertarProducto;
    private TextView btnSalir;
    private Button btnSubirImagen;
    private ImageView imgProducto;

    private Uri imagenUriSeleccionada;

    // Launcher para abrir selector de archivos
    private ActivityResultLauncher<Intent> imagenLauncher;

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_insert_productos, container, false);
        AsociarElementoXML(view);
        configurarActivityResultLauncher();

        btnSalir.setOnClickListener(v -> dismiss());

        btnSubirImagen.setOnClickListener(v -> {
            // Crear intent para seleccionar imagen
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            imagenLauncher.launch(intent);
        });

        // Aquí puedes agregar lógica para btnInsertarProducto si quieres

        return view;
    }

    private void configurarActivityResultLauncher() {
        imagenLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imagenUriSeleccionada = result.getData().getData();
                        if (imagenUriSeleccionada != null) {
                            imgProducto.setImageURI(imagenUriSeleccionada);
                            Toast.makeText(getContext(), "Imagen seleccionada correctamente", Toast.LENGTH_SHORT).show();
                            // También puedes persistir permisos si quieres:
                            getActivity().getContentResolver().takePersistableUriPermission(imagenUriSeleccionada,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        }
                    }
                }
        );
    }

    private void AsociarElementoXML(View view) {
        txtNombreProducto = view.findViewById(R.id.txtNombreProducto);
        txtDescripcionProducto = view.findViewById(R.id.txtDescripcionProducto);
        txtPrecioProducto = view.findViewById(R.id.txtPrecioProducto);
        txtStockProducto = view.findViewById(R.id.txtStockProducto);
        txtMarcaProducto = view.findViewById(R.id.txtMarcaProducto);
        txtModelo3D = view.findViewById(R.id.txtModelo3D);
        btnSubirImagen = view.findViewById(R.id.btnSubirImagen);
        spinnerCategoria = view.findViewById(R.id.spinnerCategoria);
        btnInsertarProducto = view.findViewById(R.id.btnInsertarProducto);
        btnSalir = view.findViewById(R.id.btnSalir);
        imgProducto = view.findViewById(R.id.imgProducto);
    }
}
