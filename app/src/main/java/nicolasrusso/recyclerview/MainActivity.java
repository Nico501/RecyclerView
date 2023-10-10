package nicolasrusso.recyclerview;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import nicolasrusso.recyclerview.adapters.ToDoAdapter;
import nicolasrusso.recyclerview.databinding.ActivityMainBinding;
import nicolasrusso.recyclerview.models.ToDo;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<ToDo> tareas;
    private ToDoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tareas = new ArrayList<>();
        // crearTareas();

        adapter = new ToDoAdapter(tareas, R.layout.todo_view_model, MainActivity.this);
        binding.contentMain.contenedor.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearToDo().show();
            }
        });
    }

    private void crearTareas() {
        for (int i = 0; i < 1000000; i++) {
            tareas.add(new ToDo("T"+i, "C"+ i));
        }
    }

    private AlertDialog crearToDo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear ToDo");
        builder.setCancelable(false);

        View toDoAlert = LayoutInflater.from(this).inflate(R.layout.todo_model_alert, null);
        EditText txtTitulo = toDoAlert.findViewById(R.id.txtTituloToDoModelAlert);
        EditText txtContenido = toDoAlert.findViewById(R.id.txtContenidoToDoModelAlert);
        builder.setView(toDoAlert);

        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtTitulo.getText().toString().isEmpty() && !txtContenido.getText().toString().isEmpty()){
                    tareas.add(new ToDo(txtTitulo.getText().toString(), txtContenido.getText().toString()));
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Faltan datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }
}