package dam.android.mauro.u3t4event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Arrays;

public class EventDataActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private int priority;

    private TextView tvEventName;
    private EditText etPlace;
    private RadioGroup rgPriority;
    private DatePicker dpDate;
    private TimePicker tpTime;
    private Button btnAccept, btnCancel;

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

        dpDate = findViewById(R.id.dpDate);
        // TODO Ex2 Set the view format for the DatePicker in base of the Orientation
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            dpDate.setCalendarViewShown(false);
        }

        tpTime = findViewById(R.id.tpTime);
        btnAccept = findViewById(R.id.btnAccept);
        btnCancel = findViewById(R.id.btnCancel);

        btnAccept.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        rgPriority.setOnCheckedChangeListener(this);

        // TODO Ex1.3 Restore the data for the previous event
        tvEventName.setText(inputData.getString("EventName"));
        if (inputData.size() > 1) {
            rgPriority.check(inputData.getInt("Priority"));
            etPlace.setText(inputData.getString("Place"));

            dpDate.updateDate(
                    Integer.parseInt(inputData.getString("Year", "2018")),
                    Arrays.asList(months).indexOf(inputData.getString("Month")),
                    Integer.parseInt(inputData.getString("Day"))
            );

            String[] time =  inputData.getString("Time").split(":");
            tpTime.setCurrentHour(Integer.parseInt(time[0]));
            tpTime.setCurrentMinute(Integer.parseInt(time[1]));
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
                eventData.putString("Month", months[dpDate.getMonth()]);
                eventData.putString("Day", ""+dpDate.getDayOfMonth());
                eventData.putString("Year", ""+dpDate.getYear());
                eventData.putString("Time",  (tpTime.getCurrentHour() + ":" + tpTime.getCurrentMinute()));

                // TODO Ex1.1 When touch accept send a RESULT_OK result
                setResult(RESULT_OK, activityResult);
                break;
            case R.id.btnCancel:
                eventData.putString("EventData", "");
                // TODO Ex1.1 When touch cancel send a RESULT_CANCEL result
                setResult(RESULT_CANCELED, activityResult);
                break;
        }
        activityResult.putExtras(eventData);
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        priority = checkedId;
    }
}
