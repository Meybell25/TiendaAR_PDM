package com.example.tiendaar_pdm.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tiendaar_pdm.R;
import com.example.tiendaar_pdm.adapters.OrdenDetalleAdapter;
import com.example.tiendaar_pdm.adapters.ProductoAdapter;


public class OrdenDetalleFragment extends Fragment {
    private DialogFragment dialogFragment;
    private RecyclerView rvcOrdenDetalles;
    private OrdenDetalleAdapter ordenDetalleAdapter;

    public OrdenDetalleFragment() {
        // Required empty public constructor
    }

    public static OrdenDetalleFragment newInstance(String param1, String param2) {
        OrdenDetalleFragment fragment = new OrdenDetalleFragment();
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
        View view = inflater.inflate(R.layout.fragment_orden_detalle, container, false);
        AsociarElementosXML(view);

        ordenDetalleAdapter = new OrdenDetalleAdapter(getContext(), getParentFragmentManager());
        rvcOrdenDetalles.setLayoutManager(new LinearLayoutManager(getContext()));
        rvcOrdenDetalles.setAdapter(ordenDetalleAdapter);

        return view;
    }

    public void AsociarElementosXML(View view){
        rvcOrdenDetalles = view.findViewById(R.id.rvcOrdenDetalles);
    }
}