package dp.ledtoggle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    Gpio ledPin;
    String pinName="BCM4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void initial(){
        PeripheralManagerService service = new PeripheralManagerService();
        try {

         ledPin=service.openGpio(pinName);
         ledPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

        } catch (IOException e) {
            Log.e("LedToggle", "Error on PeripheralIO API", e);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LedToggle", "Closing LED GPIO pin");
        try {
            ledPin.close();
        } catch (IOException e) {
            Log.e("LedToggle", "Error on PeripheralIO API", e);
        } finally {
            ledPin = null;
        }
    }
}
