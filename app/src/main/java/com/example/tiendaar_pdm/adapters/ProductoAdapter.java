package com.example.tiendaar_pdm.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendaar_pdm.R;
import com.example.tiendaar_pdm.dialogs.ProductosDialogo;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolderProductoAdapter> {


    private Context context;
    private FragmentManager fragmentManager;

    public ProductoAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ProductoAdapter.ViewHolderProductoAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_producto, parent, false);
        return new ViewHolderProductoAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ViewHolderProductoAdapter holder, int position) {
        holder.imgImagenProducto.setImageResource(R.drawable.refri);
        holder.lblNombreProducto.setText("Refrigerados");
        holder.lblDescripcion.setText("Este es un refrigerador moderno que se adapta a tu hogar");
        holder.lblMarca.setText("Mabe");
        holder.lblStock.setText("23");
        holder.lblPrecio.setText("$ 230");

        holder.imgImagenProducto.setImageResource(R.drawable.refri);
        holder.lblNombreProducto.setText("Refrigerados");
        holder.lblDescripcion.setText("Este es un refrigerador moderno que se adapta a tu hogar");
        holder.lblMarca.setText("Mabe");
        holder.lblStock.setText("23");
        holder.lblPrecio.setText("$ 230");

        //EVENTO DEL BOTON ELIMINAR (PERO SOLO EL DIALOGO)
        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("¿Eliminar estudiante?")
                    .setMessage("¿Estás seguro de que deseas eliminar este producto ")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        Toast.makeText(context, "Elimindo", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
        //EVENTO DEL BOTON EDITAR
        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductosDialogo depatamentoDialogo = new ProductosDialogo();
                depatamentoDialogo.show(fragmentManager, "editar");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolderProductoAdapter extends RecyclerView.ViewHolder {
        TextView lblNombreProducto, lblDescripcion, lblMarca, lblStock, lblPrecio;
        ImageButton btnEliminar, btnEditar, btnRA;
        ImageView imgImagenProducto;

        public ViewHolderProductoAdapter(@NonNull View itemView) {
            super(itemView);
            imgImagenProducto = itemView.findViewById(R.id.imgImagenProducto);
            lblNombreProducto = itemView.findViewById(R.id.lblNombreProducto);
            lblDescripcion = itemView.findViewById(R.id.lblDescripcion);
            lblMarca = itemView.findViewById(R.id.lblMarca);
            lblStock = itemView.findViewById(R.id.lblStock);
            lblPrecio = itemView.findViewById(R.id.lblPrecio);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnRA = itemView.findViewById(R.id.btnRA);



        }
    }
}
