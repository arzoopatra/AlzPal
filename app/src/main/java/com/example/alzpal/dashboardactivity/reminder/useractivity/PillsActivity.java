package com.example.alzpal.dashboardactivity.reminder.useractivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.database.Cursor;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.alzpal.dashboard.HomeActivity;
import com.example.alzpal.dashboardactivity.reminder.database.MedicalDB;
import com.example.alzpal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PillsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MedicalDB DbHelper;
    public static TextView empty_view;
    private UserListAdapter userlistAdapter;
    private FloatingActionButton add_user_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pills);

        DbHelper = MedicalDB.getInstance(this.getApplicationContext());

        empty_view = findViewById(R.id.empty_users);
        recyclerView = findViewById(R.id.user_list);
        add_user_fab = findViewById(R.id.add_user);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        userlistAdapter = new UserListAdapter(getApplicationContext(), DbHelper);

        updateUserList();

        add_user_fab.setOnClickListener(v -> myTextDialog().show());
    }

    private void updateUserList() {
        Cursor user_list = DbHelper.getUserList(DbHelper.getWritableDatabase());
        userlistAdapter.setUserData(user_list);
        userlistAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(userlistAdapter);

        if (user_list.getCount() == 0) {
            empty_view.setVisibility(View.VISIBLE);
            empty_view.setText(R.string.empty_users);
        } else {
            empty_view.setVisibility(View.GONE);
        }
    }

    private AlertDialog myTextDialog() {
        View layout = View.inflate(this, R.layout.add_user_dialog, null);
        EditText savedText = layout.findViewById(R.id.add_username);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("ADD", (dialog, which) -> {
            DbHelper.addUser(DbHelper.getWritableDatabase(), savedText.getText().toString().trim());
            updateUserList();
        });
        builder.setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());
        builder.setView(layout);
        return builder.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserList();
    }

    public void onBackButtonClick(View view) {
        Intent intent = new Intent(PillsActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
