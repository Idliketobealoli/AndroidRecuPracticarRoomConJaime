package com.example.androidrecupracticarroomconjaime;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidrecupracticarroomconjaime.data.Element;
import com.example.androidrecupracticarroomconjaime.data.RoomDB;

import java.util.List;

public class ElementRecyclerAdapter extends RecyclerView.Adapter<ElementRecyclerAdapter.ViewHolder> {
    List<Element> elements;
    private Activity context;

    RoomDB database;

    public ElementRecyclerAdapter(List<Element> elements, Activity context) {
        this.elements = elements;
        this.context = context;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ElementRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementRecyclerAdapter.ViewHolder holder, int position) {
        Element element = elements.get(position);

        database = RoomDB.getInstance(context);

        holder.tv.setText(elements.get(position).getText());
        holder.iv.setImageResource(elements.get(position).getImage());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Element elem = elements.get(holder.getAdapterPosition());
                int id = elem.getId();
                String text = elem.getText();
                int image = elem.getImage();
                int updatedImage = R.drawable.modified_picture;

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.update_dialog);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);

                dialog.show();

                EditText etDialog = dialog.findViewById(R.id.et_update);
                Button etButton = dialog.findViewById(R.id.b_update);

                etDialog.setText(text);

                etButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String updatedText = etDialog.getText().toString().trim();

                        database.mainDao().updateWithImage(updatedText, updatedImage, id);

                        elements.clear();
                        elements.addAll(database.mainDao().findAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Element elem = elements.get(holder.getAdapterPosition());

                database.mainDao().delete(elem);

                elements.clear();
                elements.addAll(database.mainDao().findAll());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv, ivEdit, ivDelete;
        TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.iv_imagen_element);
            ivEdit = itemView.findViewById(R.id.iv_edit_element);
            ivDelete = itemView.findViewById(R.id.iv_delete_element);
            tv = itemView.findViewById(R.id.tv_element);
        }
    }
}
