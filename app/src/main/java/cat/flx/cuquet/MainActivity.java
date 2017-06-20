package cat.flx.cuquet;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
implements CuquetView.CuquetViewListener, SensorEventListener {

    private CuquetView cuquetView;
    private TextView tvScore;
    private Sensor sensor;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cuquetView = (CuquetView) findViewById(R.id.cuquetView);
        Button btnNewGame = (Button) findViewById(R.id.btnNewGame);
        tvScore = (TextView) findViewById(R.id.tvScore);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvScore.setText("0");
                cuquetView.newGame();
            }
        });
        cuquetView.setCuquetViewListener(this);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

   /* @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch(event.getKeyCode()) {
            case KeyEvent.KEYCODE_A: cuquetView.update(0, +10); break;
            case KeyEvent.KEYCODE_Q: cuquetView.update(0, -10); break;
            case KeyEvent.KEYCODE_O: cuquetView.update(-10, 0); break;
            case KeyEvent.KEYCODE_P: cuquetView.update(+10, 0); break;
        }
        return super.dispatchKeyEvent(event);
    }
*/

    protected void onResume(){
       super.onResume();
        if(sensor!=null)
            sensorManager.registerListener(
                    this, sensor, SensorManager.SENSOR_DELAY_NORMAL
            );
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        cuquetView.update(-event.values[0], event.values[1]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void scoreUpdated(View view, int score) {
        tvScore.setText(Integer.toString(score));
    }

    @Override
    public void gameLost(View view) {
        Toast.makeText(this, "Has perdut!!!", Toast.LENGTH_LONG).show();
    }
}
