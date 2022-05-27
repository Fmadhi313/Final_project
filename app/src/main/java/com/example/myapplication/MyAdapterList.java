package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterList extends RecyclerView.Adapter<MyAdapterList.MyViewHolder> {

    Context context;
    ArrayList<Student> list;

    public MyAdapterList(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = list.get(position);
        holder.id.setText(student.getID());
        holder.name.setText(student.getFirstname());
        holder.surname.setText(student.getSurname());
        holder.fatherName.setText(student.getFathersname());
        holder.nationalID.setText(student.getNationalID());
        holder.dateOfBirth.setText(student.getDOB());
        holder.gender.setText(student.getGender());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView id, name, surname, fatherName, nationalID, dateOfBirth, gender;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.listFBID);
            name = itemView.findViewById(R.id.listFBFname);
            surname = itemView.findViewById(R.id.listFVSname);
            fatherName = itemView.findViewById(R.id.listFBFather);
            nationalID = itemView.findViewById(R.id.listFBNID);
            dateOfBirth = itemView.findViewById(R.id.listFBDOB);
            gender = itemView.findViewById(R.id.listFBGnder);
        }
    }
}
