package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DoctorMessageActivity;
import com.example.myapplication.PatientMessageActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Doctor;
import com.example.myapplication.models.Patient;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {

    private Context mContext;
    private List<Patient> mPatients;
    private boolean isChat;

    public PatientAdapter(Context mContext, List<Patient> mPatients, boolean isChat) {
        this.mContext = mContext;
        this.mPatients = mPatients;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public PatientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new PatientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Patient patient = mPatients.get(position);
        // Use firebase here
        holder.username.setText(patient.getFirstLegalName().concat(" " + patient.getLastLegalName()));
        // Do profile image here
        if (patient.getImageURL().equals("default")) {
            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(patient.getImageURL()).into(holder.profileImage);
        }

        if (isChat) {
            if (patient.getStatus().equals("online")) {
                holder.imageOn.setVisibility(View.VISIBLE);
                holder.imageOff.setVisibility(View.GONE);
            } else {
                holder.imageOn.setVisibility(View.GONE);
                holder.imageOff.setVisibility(View.VISIBLE);
            }
        } else {
            holder.imageOn.setVisibility(View.GONE);
            holder.imageOff.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DoctorMessageActivity.class);
                intent.putExtra("patientID", patient.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPatients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView profileImage;
        private ImageView imageOn;
        private ImageView imageOff;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.usernameTxt);
            profileImage = itemView.findViewById(R.id.profileImage);
            imageOn = itemView.findViewById(R.id.imageOn);
            imageOff = itemView.findViewById(R.id.imageOff);
        }
    }
}
