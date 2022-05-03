package com.example.samplenote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    RealmResults<Note> notesList;

    public MyAdapter(Context context, RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view1,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());
        String formattedTime = DateFormat.getDateTimeInstance().format(note.createdTime);
        holder.time.setText(formattedTime);

        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                note.deleteFromRealm();
                realm.commitTransaction();
                Toast.makeText(context.getApplicationContext(), "Note Deleted", Toast.LENGTH_LONG).show();
            }
        });



//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//                PopupMenu menu = new PopupMenu(context,view);
//                menu.getMenu().add("Delete");
//                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        if(menuItem.getTitle().equals("Delete")){
//                            Realm realm = Realm.getDefaultInstance();
//                            realm.beginTransaction();
//                            note.deleteFromRealm();
//                            realm.commitTransaction();
//                            Toast.makeText(context.getApplicationContext(), "Note Deleted", Toast.LENGTH_LONG).show();
//                        }
//                        return true;
//                    }
//                });
//                menu.show();
//
//                return true;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        TextView time;
        LinearLayout deleteIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleItem);
            description = itemView.findViewById(R.id.descriptionItem);
            time = itemView.findViewById(R.id.timeItem);
            deleteIcon = itemView.findViewById(R.id.deleteLayout);
        }
    }

}
