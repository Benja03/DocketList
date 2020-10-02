package com.example.docketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.docketlist.Modelos.Pedido;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvPedidos;
    private PedidoAdapter adaptador;
    //private SwipeRefreshLayout swipeRefreshLayout;
    private List<Pedido> listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvPedidos = findViewById(R.id.lvPedidos);

        listaPedidos = new ArrayList<>();
        this.cargarDatos(listaPedidos);

        adaptador = new PedidoAdapter(listaPedidos);
        lvPedidos.setAdapter(adaptador);

        lvPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cargarDetallePedido(position);
                //Toast.makeText(MainActivity.this, "Click en elemento "+position, Toast.LENGTH_SHORT).show();
            }
        });

        /*
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarDatos(listaPedidos);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        */
    }

    private void cargarDatos(List<Pedido> listaPedidos){
        listaPedidos.add(new Pedido(1, "Nicolas",1F, 2F, "Benjamin", "26/09"));
        listaPedidos.add(new Pedido(2, "Maria",1.5F, 2F, "Benjamin", "26/09"));
        listaPedidos.add(new Pedido(3, "Ana",3F, 0F, "Alma", "26/09"));
        listaPedidos.add(new Pedido(4, "Ezequiel",2F, 2F, "Alma", "26/09"));
        listaPedidos.add(new Pedido(5, "Marcelo",1.5F, 1.5F, "Benjamin", "19/09"));
        listaPedidos.add(new Pedido(6, "Graciela",0F, 2F, "Alma", "19/09"));
        listaPedidos.add(new Pedido(7, "Esteban",6F, 2F, "Benjamin", "19/09"));
        listaPedidos.add(new Pedido(8, "Rocio",2.5F, 2F, "Alma", "12/09"));
        listaPedidos.add(new Pedido(9, "Valeria",1F, 2F, "Benjamin", "12/09"));
        listaPedidos.add(new Pedido(10, "Julio",1F, 2F, "Alma", "12/09"));
    }

    private void cargarDetallePedido(int position){
        Pedido pedido = (Pedido) adaptador.getItem(position);

        Intent intent = new Intent(MainActivity.this, ActivityDetallePedido.class);

        intent.putExtra("E_ID", pedido.getId());
        intent.putExtra("E_NOMBRE", pedido.getNombre());
        intent.putExtra("E_FECHAENTREGA", pedido.getFecha_entrega());
        intent.putExtra("E_CANTC", pedido.getCantC());
        intent.putExtra("E_CANTA", pedido.getCantA());
        intent.putExtra("E_TOTAL", pedido.getTotal());
        intent.putExtra("E_RECORDER", pedido.getRecorder());
        intent.putExtra("E_FECHAREGISTRO", pedido.getFecha_registro());
        intent.putExtra("E_ENTREGADO", pedido.getEntregado());
        intent.putExtra("E_PAGADO", pedido.getPagado());

        startActivity(intent);
    }
}