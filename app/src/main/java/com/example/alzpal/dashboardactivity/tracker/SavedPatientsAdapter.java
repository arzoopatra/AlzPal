package com.example.alzpal.dashboardactivity.tracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.alzpal.R;
import java.util.List;

public class SavedPatientsAdapter extends RecyclerView.Adapter<SavedPatientsAdapter.ViewHolder> {
    private final Context context;
    private final List<UserDetails> patientsList;
    private final UserDetailsDao userDetailsDao;

    public SavedPatientsAdapter(Context context, List<UserDetails> patientsList, UserDetailsDao userDetailsDao) {
        this.context = context;
        this.patientsList = patientsList;
        this.userDetailsDao = userDetailsDao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_saved_patients, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDetails patient = patientsList.get(position);
        holder.textViewName.setText(patient.getName());
        holder.textViewAge.setText(String.valueOf(patient.getAge()));
        holder.textViewNumber.setText(patient.getNumber());

        holder.buttonViewLocation.setOnClickListener(v -> {
            Intent intent = new Intent(context, MapsActivity.class);
            intent.putExtra("number", patient.getNumber());
            context.startActivity(intent);
        });

        holder.buttonDelete.setOnClickListener(v -> {
            new Thread(() -> {
                userDetailsDao.delete(patient);
                ((SavedLocationActivity) context).runOnUiThread(() -> {
                    int positionToRemove = patientsList.indexOf(patient);
                    if (positionToRemove != -1) {
                        patientsList.remove(positionToRemove);
                        notifyItemRemoved(positionToRemove);
                        notifyItemRangeChanged(positionToRemove, patientsList.size());
                    }
                });
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return patientsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewName;
        public final TextView textViewAge;
        public final TextView textViewNumber;
        public final Button buttonViewLocation;
        public final Button buttonDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAge = itemView.findViewById(R.id.textViewAge);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            buttonViewLocation = itemView.findViewById(R.id.buttonViewLocation);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
