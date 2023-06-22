package fragmentsUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doit.MainActivity;
import com.example.doit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dataApp.TasksDao;
import dataApp.TasksDataBase;
import models.ToDoTasksModel;
import recycleViews.RecycleViewAdaptar;
import recycleViews.RecycleViewInterface;

public class DoneFragment extends Fragment implements RecycleViewInterface, TasksDao {
    ArrayList<ToDoTasksModel> doneTasks = new ArrayList<>();
    boolean checked;
    String body;
    RecycleViewAdaptar adaptar;
    RecyclerView recyclerView;
    ToDoTasksModel toDoTasksModel;
    TasksDataBase dataBase;
    BottomNavigationView BottomNav;
    NavDirections action;
    static int deletePosition = -1;
    static String deletedElement = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        checked = ((MainActivity) getActivity()).getChecked();


        return inflater.inflate(R.layout.fragment_done, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpDB();
        setupDone();
        ((MainActivity) getActivity()).setValue(false);


        doneTasks.clear();
        doneTasks.addAll(dataBase.tasksDao().getDoneTasks());

    if(!(deletedElement.equals("")))

    {
        for (int i = 0; i < doneTasks.size(); i++) {
            if (deletedElement.equals(doneTasks.get(i).getToDoTaskBody())) {
                toDoTasksModel = doneTasks.get(i);
                doneTasks.remove(i);
                dataBase.tasksDao().deleteTask(toDoTasksModel);
                deletedElement = "";

            }
        }
    }

        setUpRV();
        ItemTouchHelper helper =new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);



    }
    private void setUpDB(){
        dataBase = Room.databaseBuilder(getActivity().getApplicationContext(),TasksDataBase.class,"Tasks")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

    }
    private void setUpRV(){
        recyclerView =getView().findViewById(R.id.mrecycleview);
        adaptar = new RecycleViewAdaptar(getActivity(),doneTasks,  this);
        recyclerView.setAdapter(adaptar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.smoothScrollToPosition(doneTasks.size()-1);

    }
    private void setupDone(){
        if (checked){
            body= DoneFragmentArgs.fromBundle(getArguments()).getDoneTask();
            Navigation.findNavController(getView()).popBackStack();
            toDoTasksModel = new ToDoTasksModel(true,body);
            doneTasks.add(toDoTasksModel);
            dataBase.tasksDao().insertTask(toDoTasksModel);
            MainActivity.setChecked(false);

        }
    }
    ItemTouchHelper.SimpleCallback callback =new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction){
            Toast.makeText(getContext(), "item has been deleted!", Toast.LENGTH_SHORT).show();
            toDoTasksModel= doneTasks.remove(viewHolder.getAdapterPosition());
            dataBase.tasksDao().deleteTask(toDoTasksModel);
            doneTasks.clear();
            doneTasks.addAll(dataBase.tasksDao().getDoneTasks());
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

    @Override
    public void onItemClick(int position) {

    }
    public static void setPos(int pos){deletePosition =pos;}
    public static int getPos(){return deletePosition;}
    public static void setDeletedElement(String deletedEle){deletedElement=deletedEle;}
    public static String getDeletedElement(){return deletedElement;}
}
