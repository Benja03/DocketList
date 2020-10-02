package com.example.docketlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.docketlist.Modelos.Pedido;

import java.util.List;

public class PedidoAdapter extends BaseAdapter {

    private List<Pedido> listaPedidos;

    // Constructor
    public PedidoAdapter(List<Pedido> listaPedidos){
        this.listaPedidos = listaPedidos;
    }

    @Override
    public int getCount() {
        return listaPedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaPedidos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if(convertView==null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_pedidos, parent, false);
        } else{
            view = convertView;
        }

        Pedido pedido = (Pedido) getItem(position);

        TextView txt_FechaEntrega = view.findViewById(R.id.txt_FechaEntrega);
        TextView txt_NombreCliente = view.findViewById(R.id.txt_NombreCliente);
        TextView txt_CantC = view.findViewById(R.id.txt_CantC);
        TextView txt_CantA = view.findViewById(R.id.txt_CantA);

        txt_FechaEntrega.setText(pedido.getFecha_entrega());
        txt_NombreCliente.setText(pedido.getNombre());
        txt_CantC.setText(String.valueOf(pedido.getCantC()));
        txt_CantA.setText(String.valueOf(pedido.getCantA()));

        return view;
    }
}
