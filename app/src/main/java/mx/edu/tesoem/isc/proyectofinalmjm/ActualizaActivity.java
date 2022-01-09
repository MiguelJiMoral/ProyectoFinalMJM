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

import mx.edu.tesoem.isc.proyectofinalmjm.DTO.DatosDTO;
import mx.edu.tesoem.isc.proyectofinalmjm.DAO.ConexionDAO;
import mx.edu.tesoem.isc.proyectofinalmjm.DAO.DatosHelper.tabladatos;

public class ActualizaActivity extends AppCompatActivity {

    EditText txtnombre, txtedad, txtcorreo;
    DatosDTO datosDTO = new DatosDTO();
    Button btnguardar, btncancelar;
    String idactualiza="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualiza);

        Bundle datos = getIntent().getExtras();
        String idb = datos.getString("idbuscar");
        txtnombre = findViewById(R.id.txtnombreE);
        txtedad = findViewById(R.id.txtedadE);
        txtcorreo = findViewById(R.id.txtcorreoE);
        btnguardar = findViewById(R.id.btnEelimina);
        btncancelar = findViewById(R.id.btnECancelar);

        String[] parametrocondiciones = new String[]{idb};

        ConexionDAO conexionDAO = new ConexionDAO(this);
        conexionDAO.abrirConexion();
        if (conexionDAO.consultaID(parametrocondiciones)){
            datosDTO = conexionDAO.regresaID();
            txtnombre.setText(datosDTO.getNombre());
            txtedad.setText(String.valueOf(datosDTO.getEdad()));
            txtcorreo.setText(datosDTO.getCorreo());
            idactualiza = String.valueOf(datosDTO.getId());
        } else {
            Toast.makeText(this, "No se puede recuperar la info", Toast.LENGTH_SHORT).show();
        }
        conexionDAO.cerrar();
    }
    public void acciones(View v){
        switch (v.getId()){
            case R.id.btnEelimina: btnguarda();
            break;
            case R.id.btnECancelar: btncancela();
            break;
        }
    }

    private void btnguarda(){
        String[] condiciones = new String[]{idactualiza};
        ContentValues contentValues = new ContentValues();
        contentValues.put(tabladatos.TABLA_NOMBRE, txtnombre.getText().toString());
        contentValues.put(tabladatos.TABLA_EDAD, Integer.valueOf(txtedad.getText().toString()));
        contentValues.put(tabladatos.TABLA_CORREO, txtcorreo.getText().toString());

        ConexionDAO conexionDAO = new ConexionDAO(this);
        conexionDAO.abrirConexion();
        if (conexionDAO.actualiza(contentValues, condiciones)){
            Toast.makeText(this, "Se actualizaron los datos...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("vista", "Actualizar");
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else{
            Toast.makeText(this, "Error al actualizar...", Toast.LENGTH_SHORT).show();
            btnguardar.setActivated(false);
        }
    }
    private void btncancela(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}