package com.example.tiendaar_pdm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendaar_pdm.R;
import com.example.tiendaar_pdm.dialogs.CategoriasDialogo;
import com.google.android.material.button.MaterialButton;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolderCategoriaAdapter> {
    private Context context;
    private FragmentManager fragmentManager;

    public CategoriaAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public CategoriaAdapter.ViewHolderCategoriaAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iten_admin_categoria, parent, false);
        return new ViewHolderCategoriaAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.ViewHolderCategoriaAdapter holder, int position) {
        holder.imgImagenCategoria.setImageResource(R.drawable.refri);
        holder.lblNombreCatgoria.setText("Refrigerados");

        //EVENTO DEL BOTTON ELIMINAR
        holder.btnEliminarCategoria.setOnClickListener(v->{
            new AlertDialog.Builder(context)
                    .setTitle("¿Eliminar estudiante?")
                    .setMessage("¿Estás seguro de que deseas eliminar esta categoia ")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        Toast.makeText(context, "Elimindo", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
        holder.btnEditarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriasDialogo depatamentoDialogo = new CategoriasDialogo();
                depatamentoDialogo.show(fragmentManager, "editar");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolderCategoriaAdapter extends RecyclerView.ViewHolder {
        ImageView imgImagenCategoria;
        TextView lblNombreCatgoria;
        ImageButton btnEditarCategoria, btnEliminarCategoria;

        public ViewHolderCategoriaAdapter(@NonNull View itemView) {
            super(itemView);
            imgImagenCategoria  = itemView.findViewById(R.id.imgImagenCategoria);
            lblNombreCatgoria  = itemView.findViewById(R.id.lblNombreCatgoria);
            btnEditarCategoria = itemView.findViewById(R.id.btnEditarCategoria);
            btnEliminarCategoria = itemView.findViewById(R.id.btnEliminarCategoria);

        }
    }
}
