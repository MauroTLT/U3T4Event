package dam.android.mauro.u3t4event;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST = 0;

    private EditText etEventName;
    private TextView tvCurrentData;

    private Bundle eventData = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();
    }

    private void setUI() {
        etEventName = findViewById(R.id.etEventName);
        tvCurrentData = findViewById(R.id.tvCurrentData);
    }

    public void editEventData(View v) {
        Intent intent = new Intent(this, EventDataActivity.class);


        if (!eventData.isEmpty()) {
            eventData.putString("EventName", etEventName.getText().toString());
            intent.putExtras(eventData);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("EventName", etEventName.getText().toString());
            intent.putExtras(bundle);
        }

        startActivityForResult(intent, REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST) {
            // TODO Ex1.1 Do something in base of the resultCode receive
            switch (resultCode) {
                case RESULT_OK:
                    eventData = data.getExtras();
                    setData();
                    break;
                case RESULT_CANCELED:
                    break;
            }
        }
    }

    // TODO Ex3 Construct the event with the data received
    private void setData() {
        String text = getString(R.string.place, eventData.getString("Place"));

        text += "\n" +  getString(R.string.priority,
                getString(((eventData.getInt("Priority") == R.id.rbLow)? R.string.rbLow :
                        ((eventData.getInt("Priority") == R.id.rbMedium) ? R.string.rbMedium : R.string.rbHigh))));

        text += "\n" + getString(R.string.date,
                eventData.getString("Day"),
                eventData.getString("Month"),
                eventData.getString("Year"));

        text += "\n" + getString(R.string.time, eventData.getString("Time"));

        tvCurrentData.setText(text);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("EventData", eventData);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        eventData = savedInstanceState.getBundle("EventData");
        if (eventData.size() > 1) {
            setData();
        }
    }
}
