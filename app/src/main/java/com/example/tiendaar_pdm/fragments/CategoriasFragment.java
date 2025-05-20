package com.example.tiendaar_pdm.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tiendaar_pdm.R;
import com.example.tiendaar_pdm.adapters.CategoriaAdapter;
import com.example.tiendaar_pdm.adapters.ProductoAdapter;
import com.example.tiendaar_pdm.dialogs.CategoriasDialogo;
import com.example.tiendaar_pdm.dialogs.ProductosDialogo;


public class CategoriasFragment extends Fragment {
    private Button btnAbrirDialogoCategoria;
    private DialogFragment dialogFragment;
    private CategoriaAdapter categoriaAdapter;
    private RecyclerView rvcCategorias;


    public CategoriasFragment() {
        // Required empty public constructor
    }


    public static CategoriasFragment newInstance(String param1, String param2) {
        CategoriasFragment fragment = new CategoriasFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_categorias, container, false);
        AsociarElementosXML(view);

        categoriaAdapter = new CategoriaAdapter(getContext(), getParentFragmentManager());
        rvcCategorias.setLayoutManager(new LinearLayoutManager(getContext()));
        rvcCategorias.setAdapter(categoriaAdapter);



        btnAbrirDialogoCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriasDialogo dialog = new CategoriasDialogo();
                dialog.show(getParentFragmentManager(), "categoriasDialogo");
            }
        });

        return view;
    }
    public void AsociarElementosXML(View view){
        btnAbrirDialogoCategoria = view.findViewById(R.id.btnAbrirDialogoCategoria);
        rvcCategorias = view.findViewById(R.id.rvcCategorias);
    }
}