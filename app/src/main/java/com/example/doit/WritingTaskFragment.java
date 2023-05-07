package com.example.doit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fragmentsUi.TasksFragmentDirections;


public class WritingTaskFragment extends Fragment {
    Button saveBtn;
    EditText edit1;
    String writtenText;
    public interface onSomeEventListener {
        public void someEvent(String s);
    }

    onSomeEventListener someEventListener;
    NavDirections action;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_writing_task, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edit1 = view.findViewById(R.id.editText);
     saveBtn = view.findViewById(R.id.saveBtn);

        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }



            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable editable) {
              writtenText = edit1.getText().toString();
                if (!(writtenText.equals(""))) {
                    saveBtn.setTextColor(saveBtn.getContext().getResources().getColor(R.color.black));
                    saveBtn.setBackgroundColor(saveBtn.getContext().getResources().getColor(R.color.holo_green_light));
                    saveBtn.setActivated(true);
                } else {
                    saveBtn.setTextColor(saveBtn.getContext().getResources().getColor(R.color.gray));
                    saveBtn.setBackgroundColor(saveBtn.getContext().getResources().getColor(R.color.white));
                    saveBtn.setActivated(false);
                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               someEventListener.someEvent(edit1.getText().toString());
                action = WritingTaskFragmentDirections.actionWritingTaskFragmentToTasksFragment2();
                Navigation.findNavController(view).navigate(action);


            }
        });









    }

    @Override
    public void onAttach( Context context) {
        super.onAttach(requireContext());
        try {
            someEventListener = (onSomeEventListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }



}

