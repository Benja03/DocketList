package com.example.docketlist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.docketlist.Modelos.Pedido;

public class ActivityDetallePedido extends AppCompatActivity{

    private TextView txt_FechaEntrega, txt_CantCalabaza, txt_CantAcelga, txt_Total, txt_Register, txt_FechaRegistro;
    private CheckBox chkbx_Entregado, chkbx_Pagado;

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private Context ctx;
    private int idPedido;
    private boolean entregado_ant, pagado_ant;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        this.ctx = getApplicationContext();

        findViews();

        Intent intent = getIntent();
        idPedido = intent.getIntExtra("ID_PEDIDO",0);

        //abrimos db en modo escritura
        dbHelper = new DbHelper(this.ctx);
        db = dbHelper.getWritableDatabase();

        if(idPedido!=0){
            //Toast.makeText(ctx, "seleccionó: "+idPedido, Toast.LENGTH_SHORT).show();
            cargarItemSqlite(idPedido);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cargarItemSqlite(idPedido);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ContentValues cambios = new ContentValues();

        //si hubo cambios guardo
        if(entregado_ant != chkbx_Entregado.isChecked() || pagado_ant != chkbx_Pagado.isChecked()){
            if(chkbx_Entregado.isChecked()) cambios.put("entregado", 1);
            else cambios.put("entregado", 0);

            if(chkbx_Pagado.isChecked()) cambios.put("pagado", 1);
            else cambios.put("pagado", 0);

            db.update("Pedido", cambios, "idPedido=?",new String[]{String.valueOf(idPedido)});
            Toast.makeText(ctx, "Registro Grabado OK", Toast.LENGTH_SHORT).show();
        }

        db.close();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opciones_activity_detallepedido,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_pedido_volver:
                //Intent intent = new Intent(ActivityDetallePedido.this, MainActivity.class);
                //startActivity(intent);
                finish();
                return true;
            case R.id.action_pedido_editar:
                Intent i = new Intent(ActivityDetallePedido.this, Abm_Pedido.class);
                i.putExtra("ID_PEDIDO", idPedido);
                startActivity(i);
                return true;
            case R.id.action_pedido_borrar:
                // Abrir cuadro de dialogo que confirme si quiere borrar.
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /*
    * Funciones Propias
    */

    private void findViews(){
        txt_FechaEntrega = findViewById(R.id.txt_FechaEntrega);
        txt_CantCalabaza = findViewById(R.id.txt_CantCalabaza);
        txt_CantAcelga = findViewById(R.id.txt_CantAcelga);
        txt_Total = findViewById(R.id.txt_Total);
        chkbx_Entregado = findViewById(R.id.chkbx_Entregado);
        chkbx_Pagado = findViewById(R.id.chkbx_Pagado);
        txt_Register = findViewById(R.id.txt_Register);
        txt_FechaRegistro = findViewById(R.id.txt_fechaRegistro);
    }

    private void cargarItemSqlite(int idPedido) {
        //seleccionamos todos los registros
        Cursor cursor = db.rawQuery("SELECT * FROM Pedido WHERE idPedido=?", new String[]{String.valueOf(idPedido)});

        //nos posicionamos al inicio del curso
        if(cursor!=null && cursor.moveToLast()) {
            //iteramos todos los registros del cursor y llenamos array con registros
            getSupportActionBar().setTitle(cursor.getString(cursor.getColumnIndex("nombre")));

            txt_FechaEntrega.setText(cursor.getString(cursor.getColumnIndex("fechaEntrega")));
            txt_CantCalabaza.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex("cantC"))));
            txt_CantAcelga.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex("cantA"))));
            txt_Total.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex("total"))));
            chkbx_Entregado.setChecked(cursor.getInt(cursor.getColumnIndex("entregado")) == 1); //si es ==1 es true sino falso
            chkbx_Pagado.setChecked(cursor.getInt(cursor.getColumnIndex("pagado")) == 1);
            txt_Register.setText(cursor.getString(cursor.getColumnIndex("recorder")));
            txt_FechaRegistro.setText(cursor.getString(cursor.getColumnIndex("fechaRegistro")));

            entregado_ant = cursor.getInt(cursor.getColumnIndex("entregado")) == 1;
            pagado_ant = cursor.getInt(cursor.getColumnIndex("pagado")) == 1;
        }
        else{
            Toast.makeText(ctx, "No hay registros", Toast.LENGTH_SHORT).show();
        }
    }

}
