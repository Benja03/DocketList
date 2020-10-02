package com.example.docketlist;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityDetallePedido extends AppCompatActivity {

    TextView txt_FechaEntrega;
    TextView txt_CantCalabaza;
    TextView txt_CantAcelga;
    TextView txt_Total;
    CheckBox chkbx_Entregado;
    CheckBox chkbx_Pagado;
    TextView txt_Register;
    TextView txt_FechaRegistro;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        findViews();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        getSupportActionBar().setTitle(extras.getString("E_NOMBRE"));

        txt_FechaEntrega.setText(extras.getString("E_FECHAENTREGA"));
        txt_CantCalabaza.setText(String.valueOf(extras.getFloat("E_CANTC")));
        txt_CantAcelga.setText(String.valueOf(extras.getFloat("E_CANTA")));
        txt_Total.setText(String.valueOf(extras.getFloat("E_TOTAL")));
        chkbx_Entregado.setChecked(extras.getBoolean("E_ENTREGADO"));
        chkbx_Pagado.setChecked(extras.getBoolean("E_PAGADO"));
        txt_Register.setText(extras.getString("E_RECORDER"));
        txt_FechaRegistro.setText(extras.getString("E_FECHAREGISTRO"));



        //intent.putExtra("E_ID", pedido.getId());
        }

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
}
