package com.example.myapplication.ui;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.note.Note;
import com.example.myapplication.note.NoteSourceInteface;

import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public NoteSourceInteface dataSource;
    private final Fragment fragment;
    private int menuPosition;
    private MyClickListener clicListener;


    public NoteAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(NoteSourceInteface dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(MyClickListener itemClickListener) {
        clicListener = itemClickListener;
    }


    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        holder.getTextView_name().setText(dataSource.getNote(position).getTitle());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy",
                Locale.getDefault());
        holder.getTextView_data().setText(dataSource.getNote(position).getCalendarData());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    public interface MyClickListener {
        void onItemClick(int possition, Note note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        LinearLayout linearLayout;
        TextView textView_name;
        TextView textView_data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            linearLayout = itemView.findViewById(R.id.recycler_view_element);
            textView_name = itemView.findViewById(R.id.name_item);
            textView_data = itemView.findViewById(R.id.date_of_item);
            registerContextMenu(itemView);

            linearLayout.setOnClickListener(v -> {
                int possition = getAdapterPosition();
                clicListener.onItemClick(possition, dataSource.getNote(possition));
            });
            linearLayout.setOnLongClickListener(v -> {
                menuPosition = getLayoutPosition();
                itemView.showContextMenu();
                return true;
            });
        }

        private void registerContextMenu(View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(v -> {
                    menuPosition = getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public TextView getTextView_name() {
            return textView_name;
        }

        public TextView getTextView_data() {
            return textView_data;
        }
    }
}
