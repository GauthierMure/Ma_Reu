package com.example.maru.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;

import java.util.List;

public class AddMailRecyclerViewAdapter extends RecyclerView.Adapter<AddMailRecyclerViewAdapter.viewHolder>{
    private List<String> mails;
    private int listMaxSize;

    public AddMailRecyclerViewAdapter(List<String> mails, int max){
        this.mails = mails;
        listMaxSize = max;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_mail,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.mailTv.setText(mails.get(position));
        holder.getMailBtn().setOnClickListener(v -> {
            mails.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,getItemCount());
        });
    }

    @Override
    public int getItemCount() {
        return mails.size();
    }

    public int getListMaxSize(){
        return listMaxSize;
    }

    public void setListMaxSize(int size){
        this.listMaxSize = size;
    }

    public List<String> getListMails(){
        return mails;
    }

    public void addMail(String mail){
        mails.add(mail);
        notifyItemInserted(getItemCount());
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        private TextView mailTv;
        private ImageButton mailBtn;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mailBtn = itemView.findViewById(R.id.deleteMailIBtn);
            mailTv = itemView.findViewById(R.id.mailTv);

        }

        public TextView getMailTv() {
            return mailTv;
        }

        public void setMailTv(TextView mailTv) {
            this.mailTv = mailTv;
        }

        public ImageButton getMailBtn() {
            return mailBtn;
        }

        public void setMailBtn(ImageButton mailBtn) {
            this.mailBtn = mailBtn;
        }
    }
}
