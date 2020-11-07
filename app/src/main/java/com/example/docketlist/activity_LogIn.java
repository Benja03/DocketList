package com.example.docketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class activity_LogIn extends AppCompatActivity implements View.OnClickListener {

    private EditText edtxt_Login_User, edtxt_Login_Password;
    private Switch swt_Login_RememberMe;
    private Button btn_Login_Ingresar;

    private SharedPreferences preferences;
    public static final String PREFS_NAME = "mis_preferencias";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__log_in);

        getSupportActionBar().setTitle("Log in");

        findViews();

        //Shared preferences
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userName = preferences.getString("nombreUsuario","");
        String password = preferences.getString("passwordUsuario","");
        Boolean checked = preferences.getBoolean("isChecked", false);

        //cargar controles con pref obtenidas o valores por defecto
        this.cargarCredencialesPrefs(password, userName, checked);
    }

    private void findViews(){
        edtxt_Login_User = findViewById(R.id.edtxt_Login_User);
        edtxt_Login_Password = findViewById(R.id.edtxt_Login_Password);
        swt_Login_RememberMe = findViewById(R.id.swt_Login_RememberMe);
        btn_Login_Ingresar = findViewById(R.id.btn_Login_Ingresar);

        btn_Login_Ingresar.setOnClickListener(this);
    }

    private void cargarCredencialesPrefs(String password, String usrName, Boolean checked){
        edtxt_Login_Password.setText(password);
        edtxt_Login_User.setText(usrName);
        swt_Login_RememberMe.setChecked(checked);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Login_Ingresar) {
            if (swt_Login_RememberMe.isChecked()) {
                this.grabarCredencialesPrefs(edtxt_Login_Password.getText().toString(), edtxt_Login_User.getText().toString(), swt_Login_RememberMe.isChecked());
            } else {
                this.limpiarCredencialesPrefs();
            }

            Intent intent = new Intent(activity_LogIn.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void grabarCredencialesPrefs(String password, String email, Boolean checked){
        SharedPreferences.Editor editor = preferences.edit();
        // OJO: encriptar password antes de almacenar
        //String p = Utils.convertirSHA256(password);
        editor.putString("passwordUsuario", password);
        editor.putString("nombreUsuario", email);
        editor.putBoolean("isChecked", checked);
        editor.commit();
        //notificacion Mediante Snackbar -> requiere Support Design Library
        // Snackbar.make(findViewById(R.id.main_layout),"Valores Grabados OK",Snackbar.LENGTH_SHORT).show();
    }

    private void limpiarCredencialesPrefs(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        edtxt_Login_Password.setText("");
        edtxt_Login_User.setText("");
        swt_Login_RememberMe.setChecked(false);
        //notificacion Mediante Snackbar -> requiere Support Design Library
        // Snackbar.make(findViewById(R.id.main_layout),"Valores Borrados OK",Snackbar.LENGTH_SHORT).show();
    }
}