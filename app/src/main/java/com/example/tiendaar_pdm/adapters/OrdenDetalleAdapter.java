package com.example.tiendaar_pdm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendaar_pdm.R;
import com.example.tiendaar_pdm.fragments.OrdenDetalleFragment;

public class OrdenDetalleAdapter extends RecyclerView.Adapter<OrdenDetalleAdapter.ViewHolderOrdenDetalleAdapter> {
    public Context context;
    private FragmentManager fragmentManager;

    public OrdenDetalleAdapter(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public OrdenDetalleAdapter.ViewHolderOrdenDetalleAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orden_detalle, parent, false);
        return new ViewHolderOrdenDetalleAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdenDetalleAdapter.ViewHolderOrdenDetalleAdapter holder, int position) {
        holder.lblProducto.setText("Refrigerado Mabe");
        holder.lblCategoria.setText("Mabe es confiaza, Regigeradora de 6 pies");
        holder.lblPrecioUnitario.setText("$ 230");
        holder.lblestado.setText("Pendiente");
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ViewHolderOrdenDetalleAdapter extends RecyclerView.ViewHolder {
        TextView lblProducto, lblCategoria, lblPrecioUnitario, lblestado;
        public ViewHolderOrdenDetalleAdapter(@NonNull View itemView) {
            super(itemView);
            lblProducto = itemView.findViewById(R.id.lblProducto);
            lblCategoria = itemView.findViewById(R.id.lblCategoria);
            lblPrecioUnitario = itemView.findViewById(R.id.lblPrecioUnitario);
            lblestado = itemView.findViewById(R.id.lblestado);

        }
    }
}
