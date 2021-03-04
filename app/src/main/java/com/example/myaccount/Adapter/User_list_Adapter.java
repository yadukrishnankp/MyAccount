package com.example.myaccount.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccount.Activity.All_userview_Activity;
import com.example.myaccount.Activity.View_Business_Activity;
import com.example.myaccount.Model.Signup_model;
import com.example.myaccount.Network.NetworkState;
import com.example.myaccount.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User_list_Adapter extends RecyclerView.Adapter<User_list_Adapter.Myrecycleholder> {
    LayoutInflater  layoutInflater;
    Context context;
    ArrayList<Signup_model>arr=new ArrayList<>();

    public User_list_Adapter(Context context, ArrayList<Signup_model> arr) {
        this.context=context;
        this.arr=arr;
    }

    @NonNull
    @Override
    public Myrecycleholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.users_list_adapter,parent,false);
        Myrecycleholder myrecycleholder=new Myrecycleholder(view);
        return myrecycleholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Myrecycleholder holder, int position) {
        Signup_model sm=arr.get(position);
        holder.name.setText(arr.get(position).getName());
        holder.company.setText(arr.get(position).getCompanyname());
        Log.e("cm","="+arr.get(position).getCompanyname());
        holder.address.setText(arr.get(position).getAddress());
        holder.phone.setText(arr.get(position).getPhone());
        holder.email.setText(arr.get(position).getEmail());
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+arr.get(position).getPhone()));
                context.startActivity(intent);
            }
        });
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // Setting Alert Dialog Title
                alertDialogBuilder.setTitle("Confirm Delete..!!!");
                // Icon Of Alert Dialog
                alertDialogBuilder.setIcon(R.drawable.question);
                // Setting Alert Dialog Message
                alertDialogBuilder.setMessage("Are you sure,You want to Delete");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        deletedb(arr.get(position).getId());
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }

    public void deletedb(String id)
    {
        NetworkState state=new NetworkState();
        if (state.getConnectivityStatusString(context.getApplicationContext()).equals("Not connected to Internet"))
        {

            Toast.makeText(context, "Check Your connectivity", Toast.LENGTH_SHORT).show();

        }
        else
        {
            DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
            Query query=reference.child("NewRegistration").orderByChild("id").equalTo(id);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot appleSnapshot: snapshot.getChildren())
                    {
                        appleSnapshot.getRef().removeValue();
                        Log.e("deleted","=");
                    }
                    Intent i =new Intent(context.getApplicationContext(), All_userview_Activity.class);
                    i.putExtra("id",id);
                    context.startActivity(i);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }



    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class Myrecycleholder extends RecyclerView.ViewHolder {
        TextView company,name,address,phone,email;
        ImageView call,Delete;

        public Myrecycleholder(@NonNull View itemView) {
            super(itemView);
            company=itemView.findViewById(R.id.companyusa);
            name=itemView.findViewById(R.id.nameusa);
            address=itemView.findViewById(R.id.addressusa);
            phone=itemView.findViewById(R.id.phoneusa);
            email=itemView.findViewById(R.id.emailusa);
            call=itemView.findViewById(R.id.callusa);
            Delete=itemView.findViewById(R.id.delete);

        }
    }
}
