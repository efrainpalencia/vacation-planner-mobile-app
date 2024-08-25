package com.example.d308vacationplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.entities.Excursion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    public class ExcursionViewHolder extends RecyclerView.ViewHolder {

        private final TextView excursionItemView;
        private final TextView excursionItemView2;

        private ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.excursionItemView = itemView.findViewById(R.id.textView3);
            this.excursionItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Excursion current = mExcursions.get(position);
                Intent intent = new Intent(context, ExcursionDetails.class);
                intent.putExtra("excursionTitle", current.getExcursionTitle());
                intent.putExtra("date", current.getDate().toString());
                intent.putExtra("id", current.getExcursionId());
                intent.putExtra("vacId", current.getVacationId());
                context.startActivity(intent);
            });

        }
    }

    @NonNull
    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item,parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {

        if (mExcursions != null) {
            Excursion current = mExcursions.get(position);
            String name = current.getExcursionTitle();
            String currentExcDate = current.getDate().toString();
            String newExcDate = getNewExcDate(currentExcDate);

            holder.excursionItemView.setText(name);
            holder.excursionItemView2.setText(newExcDate);
        } else {
            holder.excursionItemView.setText("No excursion found.");
            holder.excursionItemView.setText("No Vacation Id found.");
        }
    }

    private static @NonNull String getNewExcDate(String currentExcDate) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        SimpleDateFormat newFormat = new SimpleDateFormat("EEE MMM dd yyyy", Locale.US);
        String newExcDate;

        try {
            Date excDate = originalFormat.parse(currentExcDate);
            assert excDate != null;
            newExcDate = newFormat.format(excDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return newExcDate;
    }

    @Override
    public int getItemCount() {

        if (mExcursions != null) {
            return mExcursions.size();
        } else {
            return 0;
        }
    }

    public void setExcursions(List<Excursion> mExcursions) {
        this.mExcursions = mExcursions;
        notifyDataSetChanged();
    }

    public ExcursionAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

}
