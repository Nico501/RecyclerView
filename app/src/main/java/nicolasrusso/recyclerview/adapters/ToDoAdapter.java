package nicolasrusso.recyclerview.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nicolasrusso.recyclerview.MainActivity;
import nicolasrusso.recyclerview.R;
import nicolasrusso.recyclerview.models.ToDo;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoVH> {
    private List<ToDo> objects;
    private int resource;
    private Context context;

    public ToDoAdapter(List<ToDo> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View toDoView = LayoutInflater.from(context).inflate(resource, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        toDoView.setLayoutParams(layoutParams);
        return new ToDoVH(toDoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoVH holder, int position) {
        ToDo toDo = objects.get(position);
        holder.lblTitulo.setText(toDo.getTitulo());
        holder.lblContenido.setText(toDo.getContenido());
        holder.lblFecha.setText(toDo.getFecha().toString());

        if (toDo.isCompletado()){
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        }else {
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        }

        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmar("¿Seguro que quieres cambiar el estado de la tarea?", toDo).show();
            }
        });

        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarBorrar("¿Seguro que quieres borrarla?", holder.getAdapterPosition()).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    private AlertDialog confirmar(String titulo, ToDo toDo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setCancelable(false);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                toDo.setCompletado(!toDo.isCompletado());
                notifyDataSetChanged();
            }
        });

        return builder.create();
    }

    private AlertDialog confirmarBorrar(String titulo, int posicion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo);
        builder.setCancelable(true);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                objects.remove(posicion);
                notifyItemRemoved(posicion);
            }
        });

        return builder.create();
    }

    public class ToDoVH extends RecyclerView.ViewHolder {
        TextView lblTitulo;
        TextView lblContenido;
        TextView lblFecha;
        ImageButton btnCompletado;
        ImageButton btnBorrar;

        public ToDoVH(@NonNull View itemView){
            super(itemView);
            lblTitulo = itemView.findViewById(R.id.lblTituloToDoViewModel);
            lblContenido = itemView.findViewById(R.id.lblContenidoToDoViewModel);
            lblFecha = itemView.findViewById(R.id.lblFechaToDoViewModel);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoViewModel);
            btnBorrar = itemView.findViewById(R.id.btnBorrarToDoViewModel);
        }
    }
}
