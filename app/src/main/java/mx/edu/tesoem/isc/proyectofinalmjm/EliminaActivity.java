package mx.edu.tesoem.isc.proyectofinalmjm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mx.edu.tesoem.isc.proyectofinalmjm.DAO.ConexionDAO;
import mx.edu.tesoem.isc.proyectofinalmjm.DTO.DatosDTO;

public class EliminaActivity extends AppCompatActivity {
    EditText txtnombre, txtedad, txtcorreo;
    DatosDTO datosDTO = new DatosDTO();
    Button btnelimina, btncancela;
    String idelimina="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elimina);

        Bundle datos = getIntent().getExtras();
        String idb = datos.getString("idbuscar");

        txtnombre = findViewById(R.id.txtnombreE);
        txtedad = findViewById(R.id.txtedadE);
        txtcorreo = findViewById(R.id.txtcorreoE);
        btnelimina = findViewById(R.id.btnEelimina);
        btncancela = findViewById(R.id.btnECancelar);

        String[] parametrocondiciones = new String[]{idb};

        ConexionDAO conexionDAO = new ConexionDAO(this);
        conexionDAO.abrirConexion();
        if (conexionDAO.consultaID(parametrocondiciones)){
            datosDTO = conexionDAO.regresaID();
            txtnombre.setText(datosDTO.getNombre());
            txtedad.setText(String.valueOf(datosDTO.getEdad()));
            txtcorreo.setText(datosDTO.getCorreo());
            idelimina = String.valueOf(datosDTO.getId());
        }else{
            Toast.makeText(this, "No se puede recuperar la información", Toast.LENGTH_SHORT).show();
        }
        conexionDAO.cerrar();
    }

    public void acciones(View v){
        switch (v.getId()){
            case R.id.btnEelimina: eliminacion();
                break;
            case R.id.btnECancelar: cancelar();
                break;
        }
    }

    private void eliminacion(){
        String[] condiciones = new String[]{idelimina};
        ConexionDAO conexionDAO = new ConexionDAO(this);
        conexionDAO.abrirConexion();
        if (conexionDAO.eliminar(condiciones)){
            Toast.makeText(this, "Se elimino correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("vista", "Elimina");
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else{
            Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            btnelimina.setActivated(false);
        }
    }
    private void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}