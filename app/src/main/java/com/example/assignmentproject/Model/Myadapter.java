package com.example.assignmentproject.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmentproject.R;
import java.util.ArrayList;
import java.util.List;

public class Myadapter extends RecyclerView.Adapter<Myadapter.Myviewholder> {

    List<User>userList;
    Context context;

    public Myadapter(Context context){
        this.userList=new ArrayList<>();
        this.context=context;

    }
    public void addAll(List<User>newl){
        int initsize=userList.size();
        userList.addAll(newl);
        notifyItemRangeChanged(initsize,newl.size());
    }
   public String getLastItemid(){

        return userList.get(userList.size()-1).getId();
   }
    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(context).inflate(R.layout.listitem,parent,false);
        return new Myviewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        holder.tvemail.setText(userList.get(position).getEmail());
        holder.tvname.setText(userList.get(position).getName());
        holder.tvid.setText(userList.get(position).getId());
        holder.imgwin.setText(userList.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder {
        TextView tvname,tvemail,tvid;
        TextView imgwin;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            tvname=(TextView)itemView.findViewById(R.id.tvname);
            tvemail=(TextView)itemView.findViewById(R.id.tvemail);
            tvid=(TextView)itemView.findViewById(R.id.tvid);
            imgwin=(TextView) itemView.findViewById(R.id.imgwin);
        }
    }
}
