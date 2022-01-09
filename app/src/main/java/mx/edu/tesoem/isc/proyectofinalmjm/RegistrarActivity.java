package mx.edu.tesoem.isc.proyectofinalmjm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mx.edu.tesoem.isc.proyectofinalmjm.DAO.ConexionDAO;
import mx.edu.tesoem.isc.proyectofinalmjm.DAO.DatosHelper.tabladatos;


public class RegistrarActivity extends AppCompatActivity {

    EditText txtnombre, txtedad, txtcorreo;
    Button btnrguardar, btnrcancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        txtnombre = findViewById(R.id.txtnombre);
        txtedad = findViewById(R.id.txtedad);
        txtcorreo = findViewById(R.id.txtcorreo);

        btnrguardar = findViewById(R.id.btnRGuardar);
        btnrcancelar = findViewById(R.id.btnRCancelar);

        btnrguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("vista", "Registrar");
                guardar();
            setResult(Activity.RESULT_OK, intent);
            finish();
            }
        });

        btnrcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    private void guardar(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(tabladatos.TABLA_NOMBRE, txtnombre.getText().toString());
        contentValues.put(tabladatos.TABLA_EDAD, Integer.valueOf(txtedad.getText().toString()));
        contentValues.put(tabladatos.TABLA_CORREO, txtcorreo.getText().toString());

        ConexionDAO conexion = new ConexionDAO(this);
        conexion.abrirConexion();
        if (conexion.insertar(contentValues)){
            Toast.makeText(this, "Se grabaron los datos correctamente", Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_LONG).show();
        }
        conexion.cerrar();
    }
}