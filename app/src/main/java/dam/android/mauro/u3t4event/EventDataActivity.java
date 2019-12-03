package dam.android.mauro.u3t4event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

public class EventDataActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private String priority = "normal";

    private TextView tvEventName;
    private RadioGroup rgPriority;
    private DatePicker dpDate;
    private TimePicker tpTime;
    private Button btnAccept, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_data);

        setUI();

        Bundle inputData = getIntent().getExtras();

        tvEventName.setText(inputData.getString("EventName"));
    }

    private void setUI() {
        tvEventName = findViewById(R.id.tvEventName);
        rgPriority = findViewById(R.id.rgPriority);
        rgPriority.check(R.id.rbMedium);
        dpDate = findViewById(R.id.dpDate);
        tpTime = findViewById(R.id.tpTime);
        btnAccept = findViewById(R.id.btnAccept);
        btnCancel = findViewById(R.id.btnCancel);

        btnAccept.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        rgPriority.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent activityResult = new Intent();
        Bundle eventData = new Bundle();
        switch (v.getId()) {
            case R.id.btnAccept:
                String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                eventData.putString("EventData", "Priority: " + priority + "\n" +
                                    "Month: " + months[dpDate.getMonth()] + "\n" +
                                    "Day: " + dpDate.getDayOfMonth() + "\n" +
                                    "Year: " + dpDate.getYear());
                break;
            case R.id.btnCancel:
                eventData.putString("EventData", "");
                break;
        }

        activityResult.putExtras(eventData);
        setResult(RESULT_OK, activityResult);

        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbLow:
                priority = "Low";
                break;
            case R.id.rbMedium:
                priority = "Medium";
                break;
            case R.id.rbHigh:
                priority = "High";
                break;
        }
    }
}
