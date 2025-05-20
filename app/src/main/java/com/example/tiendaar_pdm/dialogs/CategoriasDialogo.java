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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tiendaar_pdm.R;

public class CategoriasDialogo extends DialogFragment {

    private static final int REQUEST_IMAGEN_CATEGORIA = 1002;

    private EditText txtNombreCategoria;
    private Button btnInsertarCategoria;
    private TextView btnSalir;
    private Button btnSubirImagenCategoria;
    private ImageView imgVistaPreviaCategoria;
    private Uri imagenUriSeleccionada;

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
        View view = inflater.inflate(R.layout.dialog_insertar_categorias, container, false);
        AsociarElementoXML(view);

        btnSalir.setOnClickListener(v -> dismiss());

        btnSubirImagenCategoria.setOnClickListener(v -> seleccionarImagenDesdeGaleria());

        return view;
    }

    private void AsociarElementoXML(View view) {
        txtNombreCategoria = view.findViewById(R.id.txtNombreCategoria);
        imgVistaPreviaCategoria = view.findViewById(R.id.imgVistaPreviaCategoria);
        btnInsertarCategoria = view.findViewById(R.id.btnInsertarCategoria);
        btnSalir = view.findViewById(R.id.btnSalir);
        btnSubirImagenCategoria = view.findViewById(R.id.btnSubirImagenCategoria);
    }

    private void seleccionarImagenDesdeGaleria() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGEN_CATEGORIA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGEN_CATEGORIA && resultCode == Activity.RESULT_OK && data != null) {
            imagenUriSeleccionada = data.getData();
            if (imagenUriSeleccionada != null) {
                imgVistaPreviaCategoria.setImageURI(imagenUriSeleccionada);
            }
        }
    }
}
