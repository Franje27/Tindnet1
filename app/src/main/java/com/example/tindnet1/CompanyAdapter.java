package com.example.tindnet1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    private List<Company> companyList;

    public CompanyAdapter(List<Company> companyList) {
        this.companyList = companyList;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        Company company = companyList.get(position);
        holder.companyImage.setImageResource(company.getImageResId());
        holder.companyDescription.setText(company.getDescription());
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public static class CompanyViewHolder extends RecyclerView.ViewHolder {
        ImageView companyImage;
        TextView companyDescription;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyImage = itemView.findViewById(R.id.company_image);
            companyDescription = itemView.findViewById(R.id.company_description);
        }
    }
}



