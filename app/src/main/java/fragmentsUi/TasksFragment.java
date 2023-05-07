package fragmentsUi;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.NavDirections;
import com.example.doit.R;
import com.example.doit.WritingTaskFragment;
import com.example.doit.WritingTaskFragmentDirections;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import dataApp.TasksDao;
import dataApp.TasksDataBase;
import models.ToDoTasksModel;
import recycleViews.RecycleViewAdaptar;
import recycleViews.RecycleViewInterface;

public class TasksFragment extends Fragment implements RecycleViewInterface, TasksDao,WritingTaskFragment.onSomeEventListener {
    ArrayList<ToDoTasksModel> myTasks =new ArrayList<>();
    RecycleViewAdaptar adaptar;
    RecyclerView recyclerView;
    ToDoTasksModel toDoTasksModel;
    TasksDataBase dataBase;
    FloatingActionButton addingBtn;
    BottomNavigationView BottomNav;
    NavDirections action;
    String TextToDo="";
     WritingTaskFragment frag;
     TextView tasks;

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
        setUpDB();
       // setUpTasks();
        myTasks.addAll(dataBase.tasksDao().getAll());
        setUpRV();

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


        //recyclerView.smoothScrollToPosition(myTasks.size()-1);
    }

    @Override
    public void onItemClick(int position) {

    }

   /* public void setUpTasks() {
            TextToDo = TasksFragmentArgs.fromBundle(getArguments()).getTaskBody();
            if (!(TextToDo.equals(""))) {
                boolean isChecked = TasksFragmentArgs.fromBundle(getArguments()).getIsChecked();
                toDoTasksModel = new ToDoTasksModel(isChecked, TextToDo);
                myTasks.add(toDoTasksModel);
                dataBase.tasksDao().insertTask(toDoTasksModel);
                myTasks.clear();
                myTasks.addAll(dataBase.tasksDao().getAll());
                //recyclerView.smoothScrollToPosition(myTasks.size()-1);
                adaptar.notifyDataSetChanged();

        }*/
        @Override
        public void someEvent(String s) {
            android.app.Fragment frag1 = requireActivity().getFragmentManager().findFragmentById(R.id.tasksFragment2);
            ((TextView)frag1.getView().findViewById(R.id.tasks)).setText(s);
            Log.d("name",s);
        }


    @Override
    public void deleteTask(ToDoTasksModel toDoTasksModel) {

    }

    @Override
    public void insertTask(ToDoTasksModel toDoTasksModel) {

    }

    @Override
    public List<ToDoTasksModel> getAll() {
        return null;
    }
}