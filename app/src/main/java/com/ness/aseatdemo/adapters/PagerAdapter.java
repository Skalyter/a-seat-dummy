package com.ness.aseatdemo.adapters;

import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import com.ness.aseatdemo.notifications.AlarmTrigger;
import com.ness.aseatdemo.notifications.NotificationService;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import static com.ness.aseatdemo.notifications.AlarmTrigger.KEY_MESSAGE;
import static com.ness.aseatdemo.notifications.AlarmTrigger.KEY_MILLIS;
import static com.ness.aseatdemo.notifications.NotificationService.TAG;

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
            String message = String.format("You booked a seat at office @ %s. Don't be late!",
                    holder.startTime.getText().toString());
            //todo replace this calendar with relevant values
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 7);

            Intent serviceIntent = new Intent(context, NotificationService.class);
            serviceIntent.putExtra(KEY_MESSAGE, message);
            serviceIntent.putExtra(KEY_MILLIS, calendar.getTimeInMillis());

            context.startService(serviceIntent);

//            AlarmTrigger.createNotification(context, message, calendar.getTimeInMillis());

            Toast.makeText(context, "Seat booked", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onBindViewHolder: notification triggered");
        });
    }

    @Override
    public int getItemCount() {
        return pages.size();
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
