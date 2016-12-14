package dp.ledtoggle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    //Pin for raspberry pi
    Gpio ledPin;
    String pinName="BCM4"; //using pin BCM4
    ToggleButton ledButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();

    }

    void initial(){
        ledButton= (ToggleButton) findViewById(R.id.LEDtoggleButton);
        PeripheralManagerService service = new PeripheralManagerService(); //connecting to io service of raspberry pi
        try {


         ledPin=service.openGpio(pinName); //connecting to pin
         ledPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW); //setting the pin to low in first attempt
            ledOnOff();

        } catch (IOException e) {
            Log.e("LedToggle", "Error on PeripheralIO API", e);
        }
    }

    void ledOnOff(){
        ledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ledButton.isChecked()) { //toggle button
                    try {
                        ledPin.setValue(!ledPin.getValue()); //getting the value of pin high or low and making it inverse
                    Toast.makeText(MainActivity.this,"LED ON", Toast.LENGTH_SHORT).show();}
                    catch (IOException e){
                            Log.e("LedToggle", "Error on PeripheralIO API", e);}
                    }
                else {
                    try {
                        ledPin.setValue(!ledPin.getValue());
                        Toast.makeText(MainActivity.this,"LED OFF", Toast.LENGTH_SHORT).show();}
                    catch (IOException e){
                        Log.e("LedToggle", "Error on PeripheralIO API", e);}
                }

            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LedToggle", "Closing LED GPIO pin");
        try {
            ledPin.close(); //when the application closes it also cloases connection to the pin
        } catch (IOException e) {
            Log.e("LedToggle", "Error on PeripheralIO API", e);
        } finally {
            ledPin = null;
        }
    }
}
