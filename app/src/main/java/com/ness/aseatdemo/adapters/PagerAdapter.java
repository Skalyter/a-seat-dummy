package com.ness.aseatdemo.adapters;

import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ness.aseatdemo.R;
import com.ness.aseatdemo.notifications.BackgroundService;
import com.ness.aseatdemo.notifications.BootCompleteReceiver;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import static com.ness.aseatdemo.notifications.NotificationService.TAG;
import static com.ness.aseatdemo.notifications.NotificationService.TAG_MESSAGE;
import static com.ness.aseatdemo.notifications.NotificationService.TAG_MILLIS;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.PageHolder> {

    private final Context context;
    private final List<String> pages;

    public PagerAdapter(Context context, List<String> pages) {
        this.context = context;
        this.pages = pages;
    }

    @NonNull
    @Override
    public PageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_book_layout, parent, false);
        return new PageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageHolder holder, int position) {

        holder.startTime.setOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                holder.startTime.setText(String.format("%d:%d", hourOfDay, minute));
            }, LocalTime.now().getHour(), LocalTime.now().getMinute(), true);
            dialog.show();
        });

        holder.endTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                TimePickerDialog dialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
                    holder.endTime.setText(String.format("%d:%d", hourOfDay, minute));
                }, LocalTime.now().getHour(), LocalTime.now().getMinute(), true);
                dialog.show();
            }
        });

        holder.chooseSeat.setOnClickListener(v -> {

        });

        holder.saveSeat.setOnClickListener(v -> {

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 2);
//            cal.add(Calendar.SECOND, 5);

            long millis = cal.getTimeInMillis();

            String message = String.format("You booked a seat at office @ %s. Don't be late!",
                    holder.startTime.getText().toString());

            createNotification(message, millis);

            addNotificationTimeToSharedPref(message, millis);

            Toast.makeText(context, "Seat booked", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onBindViewHolder: notification triggered");
        });
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    private void createNotification(String message, long millis) {

        Intent intent = new Intent(context, BackgroundService.class);

        intent.putExtra(TAG_MESSAGE, message);
        intent.putExtra(TAG_MILLIS, millis);

        context.startService(intent);
    }

    private void addNotificationTimeToSharedPref(String message, long millis) {
        SharedPreferences sharedPreferences
                = context.getApplicationContext().getSharedPreferences("notifications", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(TAG_MILLIS, millis);
        editor.putString(TAG_MESSAGE, message);
        editor.apply();
        Log.d(TAG, "addNotificationTimeToSharedPref:" + message);
    }

    public static class PageHolder extends RecyclerView.ViewHolder {

        EditText startTime, endTime;
        TextView chosenSeat;
        Button chooseSeat, saveSeat;

        public PageHolder(@NonNull View itemView) {
            super(itemView);

            startTime = itemView.findViewById(R.id.time_picker_start);
            endTime = itemView.findViewById(R.id.time_picker_end);

            chosenSeat = itemView.findViewById(R.id.seat_value);

            chooseSeat = itemView.findViewById(R.id.button_choose_seat);
            saveSeat = itemView.findViewById(R.id.button_save_seat);
        }
    }
}
