package com.example.tiendaar_pdm.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tiendaar_pdm.R;
import com.example.tiendaar_pdm.adapters.ProductoAdapter;
import com.example.tiendaar_pdm.dialogs.ProductosDialogo;


public class ProductosFragment extends Fragment {
    private Button btnAbrirDialogoProductos;
    private DialogFragment dialogFragment;
    private ProductoAdapter productoAdapter;
    private RecyclerView rvcProductos;

    public ProductosFragment() {
        // Required empty public constructor
    }


    public static ProductosFragment newInstance(String param1, String param2) {
        ProductosFragment fragment = new ProductosFragment();
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
        View  view = inflater.inflate(R.layout.fragment_productos, container, false);

        AsociarElementoXML(view);

        productoAdapter = new ProductoAdapter(getContext(), getParentFragmentManager());
        rvcProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvcProductos.setAdapter(productoAdapter);


        btnAbrirDialogoProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductosDialogo dialog = new ProductosDialogo();
                dialog.show(getParentFragmentManager(), "productosDialogo");
            }
        });
        return view;


    }
    private  void AsociarElementoXML(View view){
        btnAbrirDialogoProductos = view.findViewById(R.id.btnAbrirDialogoProductos);
        rvcProductos = view.findViewById(R.id.rvcProductos);
    }
}