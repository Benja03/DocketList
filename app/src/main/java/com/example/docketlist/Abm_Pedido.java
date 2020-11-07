package com.example.docketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class Abm_Pedido extends AppCompatActivity implements View.OnClickListener{

    EditText edtxt_abm_Nombre, edtxt_abm_cantC, edtxt_abm_CantA, edtxtDate_abm_FechaEntrega;
    Button  btn_abm_Guardar, btn_abm_Cancelar;

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private Context ctx;

    private SharedPreferences preferences;
    public static final String PREFS_NAME = "mis_preferencias";

    int idPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abm__pedido);

        this.ctx = this.getApplicationContext();

        Intent i = getIntent();
        idPedido = i.getIntExtra("ID_PEDIDO", 0);

        findViews();

        //abrimos db en modo escritura
        dbHelper = new DbHelper(this.ctx);
        db = dbHelper.getWritableDatabase();

        if(idPedido!=0){
            Toast.makeText(ctx, "seleccionó: "+idPedido, Toast.LENGTH_SHORT).show();
            cargarItemSqlite(idPedido);
        }

    }

    void findViews(){
        edtxt_abm_Nombre = findViewById(R.id.edtxt_abm_Nombre);
        edtxt_abm_cantC = findViewById(R.id.edtxt_abm_CantC);
        edtxt_abm_CantA = findViewById(R.id.edtxt_abm_CantA);
        edtxtDate_abm_FechaEntrega = findViewById(R.id.edtxtDate_abm_FechaEntrega);
        btn_abm_Guardar = findViewById(R.id.btn_abm_Guardar);
        btn_abm_Cancelar = findViewById(R.id.btn_abm_Cancelar);

        btn_abm_Guardar.setOnClickListener(this);
        btn_abm_Cancelar.setOnClickListener(this);
    }

    void cargarItemSqlite(int idEmpleado){

        Cursor cursor = db.rawQuery("SELECT * FROM Pedido WHERE idPedido=?", new String[]{String.valueOf(idEmpleado)});

        if(cursor!=null && cursor.moveToLast()) {
            edtxt_abm_Nombre.setText(cursor.getString(cursor.getColumnIndex("nombre")));
            edtxt_abm_cantC.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex("cantC"))));
            edtxt_abm_CantA.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex("cantA"))));
            edtxtDate_abm_FechaEntrega.setText(cursor.getString(cursor.getColumnIndex("fechaEntrega")));
        }
        else{
            Toast.makeText(ctx, "No hay registros", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void agregarItemSqlite(){

        //abrimos db en modo escritura
        dbHelper = new DbHelper(this.ctx);
        db = dbHelper.getWritableDatabase();

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userName = preferences.getString("nombreUsuario","Benjamin");

        Float cantC, cantA, total;

        // verificamos que los valores sean validos
        if(validarItems()){
            cantC = Float.parseFloat(edtxt_abm_cantC.getText().toString());
            cantA = Float.parseFloat(edtxt_abm_CantA.getText().toString());
            total = calcularTotal(cantA, cantC);

            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("nombre",edtxt_abm_Nombre.getText().toString());
            nuevoRegistro.put("cantC", cantC);
            nuevoRegistro.put("cantA", cantA);
            nuevoRegistro.put("total", total);
            nuevoRegistro.put("fechaEntrega",edtxtDate_abm_FechaEntrega.getText().toString());
            nuevoRegistro.put("fechaRegistro", DateFormat.getDateTimeInstance().format(new Date()));
            nuevoRegistro.put("recorder", userName);
            nuevoRegistro.put("entregado",0);
            nuevoRegistro.put("pagado",0);

            //insertamos registro nuevo
            db.insert("Pedido", null, nuevoRegistro);

            Toast.makeText(ctx, "Registro Nuevo Grabado OK", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ctx, "Verifique datos inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    boolean validarItems(){
        boolean valido = true;
        if(edtxt_abm_Nombre.getText().toString().isEmpty()){
            edtxt_abm_Nombre.setError("Debe completar este campo");
            valido = false;
        }
        if(edtxt_abm_cantC.getText().toString().isEmpty()){
            edtxt_abm_cantC.setError("Debe completar este campo");
            valido = false;
        }
        if(edtxt_abm_CantA.getText().toString().isEmpty()){
            edtxt_abm_CantA.setError("Debe completar este campo");
            valido = false;
        }
        if(edtxtDate_abm_FechaEntrega.getText().toString().isEmpty()){
            edtxtDate_abm_FechaEntrega.setError("Debe completar este campo");
            valido = false;
        }

        return valido;
    }

    private void editarItem(){
        /*UPDATE table_name
        SET column1 = value1, column2 = value2, ...
        WHERE condition;*/

        //abrimos db en modo escritura
        dbHelper = new DbHelper(this.ctx);
        db = dbHelper.getWritableDatabase();

        ContentValues registroEditado = new ContentValues();
        registroEditado.put("nombre", edtxt_abm_Nombre.getText().toString());
        registroEditado.put("cantC", edtxt_abm_cantC.getText().toString());
        registroEditado.put("cantA", edtxt_abm_CantA.getText().toString());
        registroEditado.put("fechaEntrega", edtxtDate_abm_FechaEntrega.getText().toString());
        registroEditado.put("fechaRegistro", DateFormat.getDateTimeInstance().format(new Date()));

        db.update("Pedido", registroEditado, "idPedido=?", new String[]{String.valueOf(idPedido)});
        Toast.makeText(ctx, "Registro Actualizado OK", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btn_abm_Guardar:
               if(idPedido == 0) agregarItemSqlite();
               else              editarItem();
               finish();
               //Intent intent = new Intent(Abm_Pedido.this, MainActivity.class);
               //startActivity(intent);
               break;
           case R.id.btn_abm_Cancelar:
               Toast.makeText(ctx, "Cancelacion", Toast.LENGTH_SHORT).show();
               finish();
       }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private Float calcularTotal(Float cantA, Float cantC){
        float total = cantA + cantC;

        if(total>=9) return (total-9) * 200 + 1620;
        if(total>=6) return (total-6) * 200 + 1080;
        if(total>=3) return (total-3) * 200 + 540;
        return total*200;
    }
}