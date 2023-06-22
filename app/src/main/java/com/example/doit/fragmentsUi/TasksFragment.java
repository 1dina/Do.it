package com.example.doit.fragmentsUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.NavDirections;

import com.example.doit.MainActivity;
import com.example.doit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import com.example.doit.dataApp.TasksDao;
import com.example.doit.dataApp.TasksDataBase;

import com.example.doit.models.ToDoTasksModel;
import com.example.doit.recycleViews.RecycleViewAdaptar;
import com.example.doit.recycleViews.RecycleViewInterface;

public class TasksFragment extends Fragment implements RecycleViewInterface, TasksDao {
    ArrayList<ToDoTasksModel> myTasks =new ArrayList<>();
    RecycleViewAdaptar adaptar;
    RecyclerView recyclerView;
    ToDoTasksModel toDoTasksModel;
    TasksDataBase dataBase;
    FloatingActionButton addingBtn;
    BottomNavigationView BottomNav;
    NavDirections action;
    String TextToDo="";
    boolean found ;
    boolean firstTime;
    static int deletePosition=-1;
    static int addPosition=-1;
    static boolean isChecked=false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     addingBtn=view.findViewById(R.id.add_fab);
     BottomNav=  getActivity().findViewById(R.id.bottom_nav);
        firstTime=((MainActivity)getActivity()).getTime();
        setUpDB();
        setUpTasks();
            myTasks.clear();
            myTasks.addAll(dataBase.tasksDao().getTasks());
        if (deletePosition!=-1){
            toDoTasksModel=myTasks.get(deletePosition);
            myTasks.remove(deletePosition);
            dataBase.tasksDao().deleteTask(toDoTasksModel);
            deletePosition=-1;
        }
        setUpRV();
        ItemTouchHelper helper =new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);



        addingBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             action = TasksFragmentDirections.actionTasksFragment2ToWritingTaskFragment();
             Navigation.findNavController(view).navigate(action);

         }
     });


    }
    private void setUpDB(){
        dataBase = Room.databaseBuilder(getActivity().getApplicationContext(),TasksDataBase.class,"Tasks")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
    private void setUpRV(){
        recyclerView =getView().findViewById(R.id.mrecycleview);
        adaptar = new RecycleViewAdaptar(getActivity(),myTasks,this);
        recyclerView.setAdapter(adaptar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    @Override
    public void onItemClick(int position) {

    }

    public void setUpTasks() {
        found=((MainActivity)getActivity()).getValue();
        if(found||addPosition!=-1){
            TextToDo = TasksFragmentArgs.fromBundle(getArguments()).getTaskBody();
            if (!(TextToDo.equals(""))) {
                if (addPosition!=-1) Navigation.findNavController(getView()).popBackStack();
              toDoTasksModel = new ToDoTasksModel(false,TextToDo);
                myTasks.add(toDoTasksModel);
                dataBase.tasksDao().insertTask(toDoTasksModel);
                myTasks.clear();
                myTasks.addAll(dataBase.tasksDao().getTasks());
                ((MainActivity)getActivity()).setValue(false);
                addPosition=-1;

}

        }}


    ItemTouchHelper.SimpleCallback callback =new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction){
            Toast.makeText(getContext(), "item has been deleted!", Toast.LENGTH_SHORT).show();
             toDoTasksModel= myTasks.remove(viewHolder.getAdapterPosition());
            dataBase.tasksDao().deleteTask(toDoTasksModel);
            myTasks.clear();
            myTasks.addAll(dataBase.tasksDao().getTasks());
            adaptar.notifyDataSetChanged();
        }
    };




    @Override
    public void deleteTask(ToDoTasksModel toDoTasksModel) {

    }

    @Override
    public void insertTask(ToDoTasksModel toDoTasksModel) {

    }

    @Override
    public List<ToDoTasksModel> getTasks() {
        return null;
    }

    @Override
    public List<ToDoTasksModel> getDoneTasks() {
        return null;
    }
    public static void setPos(int pos){deletePosition =pos;}
    public static int getPos(){return deletePosition;}
    public static void setAddPosition(int pos){addPosition =pos;}
    public static int getAddPosition(){return addPosition;}
    public static void setIsChecked(boolean check){isChecked =check;}
    public static boolean getIsChecked(){return isChecked;}

}