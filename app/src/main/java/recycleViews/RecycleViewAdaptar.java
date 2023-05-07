package recycleViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doit.R;

import java.util.ArrayList;

import models.ToDoTasksModel;

public class RecycleViewAdaptar extends RecyclerView.Adapter<RecycleViewAdaptar.MyViewHolder>{
    ArrayList<ToDoTasksModel> MyTasksToDo;
    private final RecycleViewInterface recycleViewInterface;
    Context context;
    int mExpandedPosition =-1;

    public RecycleViewAdaptar(Context context, ArrayList<ToDoTasksModel>MyTasksToDo, RecycleViewInterface recycleViewInterface) {
        this.recycleViewInterface = recycleViewInterface;
        this.context=context;
        this.MyTasksToDo=MyTasksToDo;
    }

    @NonNull
    @Override
    public RecycleViewAdaptar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {      LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_card_view,parent,false);

        return new RecycleViewAdaptar.MyViewHolder(view,recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdaptar.MyViewHolder holder, int position) {
        holder.text.setText(MyTasksToDo.get(position).getToDoTaskBody());
        holder.checkBox.setChecked(MyTasksToDo.get(position).isDone());

    }

    @Override
    public int getItemCount() {
        return MyTasksToDo.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView text;
        public MyViewHolder(View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);
            text = itemView.findViewById(R.id.tasksBody);
            checkBox =itemView.findViewById(R.id.checkbox);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recycleViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recycleViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
