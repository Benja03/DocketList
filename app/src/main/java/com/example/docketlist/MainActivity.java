package com.example.docketlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Pedido> listaPedidos;
    private Context ctx;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.ctx = getApplicationContext();

        lvPedidos = findViewById(R.id.lvPedidos);

        listaPedidos = new ArrayList<>();
        cargarDatosSqlite();

        adaptador = new PedidoAdapter(listaPedidos);
        lvPedidos.setAdapter(adaptador);

        lvPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cargarDetallePedido(position);
                //Toast.makeText(MainActivity.this, "Click en elemento "+position, Toast.LENGTH_SHORT).show();
            }
        });


        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(){
                adaptador.notifyDataSetChanged();
                cargarDatosSqlite();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adaptador.notifyDataSetChanged();
        cargarDatosSqlite();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opciones_mainactivity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_pedido_agregar:
                Intent i = new Intent(MainActivity.this, Abm_Pedido.class);
                i.putExtra("ID_PEDIDO", 0);
                startActivity(i);
                return true;
            case R.id.action_pedido_actualizar:
                adaptador.notifyDataSetChanged();
                cargarDatosSqlite();
                Toast.makeText(ctx, "actualizado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /*
    *  Funciones Propias
    */

    private void cargarDatosSqlite(){
        // obtenemos datos de SQLite
        //abrimos db en modo lectura
        dbHelper = new DbHelper(this.ctx);
        db = dbHelper.getReadableDatabase();

        // limpiamos lista
        listaPedidos.clear();

        //seleccionamos todos los registros
        Cursor cursor = db.rawQuery("SELECT * FROM Pedido",null);

        //nos posicionamos al inicio del curso
        if(cursor.moveToFirst()) {

            //iteramos todos los registros del cursor y llenamos array con registros
            while (!cursor.isAfterLast()) {

                Pedido item = new Pedido();
                item.setId(cursor.getInt(cursor.getColumnIndex("idPedido")));
                item.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                item.setFecha_registro(cursor.getString(cursor.getColumnIndex("fechaRegistro")));
                item.setFecha_entrega(cursor.getString(cursor.getColumnIndex("fechaEntrega")));
                item.setCantA(cursor.getFloat(cursor.getColumnIndex("cantA")));
                item.setCantC(cursor.getFloat(cursor.getColumnIndex("cantC")));
                item.setTotal(cursor.getFloat(cursor.getColumnIndex("total")));
                item.setRecorder(cursor.getString(cursor.getColumnIndex("recorder")));
                item.setEntregado(cursor.getInt(cursor.getColumnIndex("entregado")));
                item.setPagado(cursor.getInt(cursor.getColumnIndex("pagado")));

                listaPedidos.add(item);

                cursor.moveToNext(); // corremos a siguiente posición del curso
            }
        }
        else{
            Toast.makeText(ctx, "No hay registros", Toast.LENGTH_SHORT).show();
        }

        // cerramos conexion SQLite
        db.close();
    }

    private void cargarDetallePedido(int position){

        int ID = (int) adaptador.getItemId(position);

        Intent intent = new Intent(MainActivity.this, ActivityDetallePedido.class);
        intent.putExtra("ID_PEDIDO", ID);
        startActivity(intent);
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
}