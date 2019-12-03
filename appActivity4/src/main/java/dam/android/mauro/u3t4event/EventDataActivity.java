package dam.android.mauro.u3t4event;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Arrays;

public class EventDataActivity extends AppCompatActivity implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private int priority;

    // TODO Ex4 New TextView to select the Date and Time
    private TextView tvEventName, tvDate, tvTime;
    private EditText etPlace;
    private RadioGroup rgPriority;
    private Button btnAccept, btnCancel;

    // TODO Ex4 Variables to save the Data and Time selected from the Fragments
    private int day, month, year, hour, minute;

    // TODO Ex1.2 Create array of months with an string-array resource
    private String[] months;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_data);

        // TODO Ex1.2 Initialize the array
        months = getResources().getStringArray(R.array.months);

        priority = R.id.rbMedium;

        setUI();
    }

    private void setUI() {
        Bundle inputData = getIntent().getExtras();

        tvEventName = findViewById(R.id.tvEventName);
        etPlace = findViewById(R.id.etEventPlace);
        rgPriority = findViewById(R.id.rgPriority);
        rgPriority.check(R.id.rbMedium);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        btnAccept = findViewById(R.id.btnAccept);
        btnCancel = findViewById(R.id.btnCancel);

        btnAccept.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        // TODO Ex4 Listen to open the Fragments
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        rgPriority.setOnCheckedChangeListener(this);

        // TODO Ex1.3 Restore the data for the previous event
        tvEventName.setText(inputData.getString("EventName"));
        if (inputData.size() > 1) {
            rgPriority.check(inputData.getInt("Priority"));
            etPlace.setText(inputData.getString("Place"));
            tvDate.setText(getString(R.string.date,
                    inputData.getString("Day"),
                    ""+(Arrays.asList(months).indexOf(inputData.getString("Month"))+1),
                    inputData.getString("Year")));
            tvTime.setText(getString(R.string.time, inputData.getString("Time")));
        }
    }

    @Override
    public void onClick(View v) {
        Intent activityResult = new Intent();
        Bundle eventData = new Bundle();
        switch (v.getId()) {
            case R.id.btnAccept:
                // TODO Ex1.3 Send the event data in a better form
                eventData.putInt("Priority", priority);
                eventData.putString("Place", etPlace.getText().toString());
                eventData.putString("Month", months[month]);
                eventData.putString("Day", ""+day);
                eventData.putString("Year", ""+year);
                eventData.putString("Time",  (hour + ":" + minute));

                // TODO Ex1.1 When touch accept send a RESULT_OK result
                setResult(RESULT_OK, activityResult);
                break;
            case R.id.btnCancel:
                eventData.putString("EventData", "");
                // TODO Ex1.1 When touch cancel send a RESULT_CANCEL result
                setResult(RESULT_CANCELED, activityResult);
                break;

            // TODO Ex4 Show the Fragments when click in one of them
            case R.id.tvDate:
                DatePickerFragment dpFragment = DatePickerFragment.newInstance(this);
                dpFragment.show(getSupportFragmentManager(), "datePicker");
                return;// To exit the method without execute finish()
            case R.id.tvTime:
                TimePickerFragment tpFragment = TimePickerFragment.newInstance(this);
                tpFragment.show(getSupportFragmentManager(), "timePicker");
                return;// To exit the method without execute finish()
        }
        activityResult.putExtras(eventData);
        finish();
    }

    //TODO Ex4 Listen for user Date selection
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        tvDate.setText(getString(R.string.date,
                        String.valueOf(day),
                        String.valueOf(month+1),
                        String.valueOf(year)));
    }

    //TODO Ex4 Listen for user Time selection
    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
        tvTime.setText(getString(R.string.time, (hourOfDay + ":" + minute)));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        priority = checkedId;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}
}
