package mx.edu.tesoem.isc.proyectofinalmjm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mx.edu.tesoem.isc.proyectofinalmjm.DAO.ConexionDAO;
import mx.edu.tesoem.isc.proyectofinalmjm.DTO.DatosDTO;

public class ListarActivity extends AppCompatActivity {

    Button btnLregresa, btnLcancelar;
    GridView gvdatos;
    List<DatosDTO> lista = new ArrayList<DatosDTO>();
    ArrayList<String> listagrid = new ArrayList<String>();
    String seleccionid ="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        btnLregresa = findViewById(R.id.btnLregresa);
        btnLcancelar = findViewById(R.id.btnLcancelar);
        gvdatos = findViewById(R.id.gvDatos);

        CargarDatos();
        gvdatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                seleccionid = listagrid.get(i);
                Toast.makeText(ListarActivity.this, "Valor Seleccionado: " + seleccionid, Toast.LENGTH_SHORT).show();
            }
        });

        btnLregresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("vista", "Listar");
                intent.putExtra("idregresa", seleccionid);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        btnLcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }
    private void CargarDatos(){
        ConexionDAO conexion = new ConexionDAO(this);
        conexion.abrirConexion();
        if (conexion.cargartodos()){
            lista = conexion.getListado();
            CargarGrid();
        } else{
            Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            conexion.cerrar();
        }
    }

    private void CargarGrid(){
        DatosDTO datosDTO;
        ArrayAdapter<String> adaptador;
        listagrid.add("ID");
        listagrid.add("NOMBRE");
        for (int a =0; a<lista.size(); a++){
            datosDTO = new DatosDTO();
            datosDTO = lista.get(a);
            listagrid.add(String.valueOf(datosDTO.getId()));
            listagrid.add(datosDTO.getNombre());
        }
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listagrid);
        gvdatos.setAdapter(adaptador);
    }
}