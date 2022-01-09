package mx.edu.tesoem.isc.proyectofinalmjm;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Principal extends AppCompatActivity {

    String idprocesa = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent Data = result.getData();
                String vistaregresa = Data.getStringExtra("vista");
                if (vistaregresa.equals("Listar")) {
                    idprocesa = Data.getStringExtra("idregresa");
                }
                Toast.makeText(Principal.this, "Regresamos de la actividad correctamente", Toast.LENGTH_LONG).show();
            } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                Toast.makeText(Principal.this, "Se cancela la operacion", Toast.LENGTH_LONG).show();
            }
        }
    });

    public void Acciones(View v) {
        switch (v.getId()) {
            case R.id.btningresar:
                lanzaInsertar();
                break;
            case R.id.btnlistar: LanzaListar();
                break;
            case R.id.btnactualizar: LanzaActualiza();
                break;
            case R.id.btneliminar: LanzaElimina();
            case R.id.btnsalir: finish();
        }
    }

    private void lanzaInsertar() {
        Intent intent = new Intent(this, RegistrarActivity.class);
        activityResultLauncher.launch(intent);
    }

    private void LanzaListar() {
        Intent intent = new Intent(this, ListarActivity.class);
        activityResultLauncher.launch(intent);
    }

    private void LanzaActualiza(){
        Intent intent = new Intent(this,ActualizaActivity.class);
        intent.putExtra("idbuscar",idprocesa);
        activityResultLauncher.launch(intent);
    }

    private void LanzaElimina(){
        Intent intent = new Intent(this,EliminaActivity.class);
        intent.putExtra("idbuscar",idprocesa);
        activityResultLauncher.launch(intent);
    }
}