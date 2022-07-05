package com.example.maru.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;

import java.util.List;

public class MailListAdapter extends RecyclerView.Adapter<MailListAdapter.ViewHolder> {

    private List<String> mMails;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mailTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mailTv = itemView.findViewById(R.id.simpleTv);
        }

        public TextView getMailTv() {
            return mailTv;
        }

        public void setMailTv(TextView mailTv) {
            this.mailTv = mailTv;
        }
    }

    public MailListAdapter (List<String> mails){
        mMails = mails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_textview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getMailTv().setText(mMails.get(position));
    }

    @Override
    public int getItemCount() {
        return mMails.size();
    }
}
