package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.PatientMessageActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private Context mContext;
    private List<Doctor> mDoctors;
    private boolean isChat;

    public DoctorAdapter(Context mContext, List<Doctor> mDoctors, boolean isChat) {
        this.mContext = mContext;
        this.mDoctors = mDoctors;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new DoctorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Doctor doctor = mDoctors.get(position);
        // Use firebase here
        holder.username.setText(doctor.getFirstLegalName().concat(" " + doctor.getLastLegalName()));
        // Do profile image here
        if (doctor.getImageURL().equals("default")) {
            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(doctor.getImageURL()).into(holder.profileImage);
        }

        if (isChat) {
            if (doctor.getStatus().equals("online")) {
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
                Intent intent = new Intent(mContext, PatientMessageActivity.class);
                intent.putExtra("doctorID", doctor.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
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
