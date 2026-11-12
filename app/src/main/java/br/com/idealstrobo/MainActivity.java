package br.com.idealstrobo;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final int LED_COUNT = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 32, 24, 24);
        root.setBackgroundColor(Color.rgb(18, 20, 25));

        TextView title = new TextView(this);
        title.setText("iDeal Strobo");
        title.setTextColor(Color.WHITE);
        title.setTextSize(26);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView status = new TextView(this);
        status.setText(
            "Prévia do padrão: 200 LEDs\n" +
            "A cada 4 LEDs: 3 branco quente fixos + 1 branco frio piscando rápido.\n\n" +
            "Esta versão é apenas uma prévia visual. Ainda não envia comandos Bluetooth."
        );
        status.setTextColor(Color.LTGRAY);
        status.setTextSize(16);
        status.setPadding(0, 24, 0, 24);
        root.addView(status);

        ScrollView scroll = new ScrollView(this);
        LinearLayout grid = new LinearLayout(this);
        grid.setOrientation(LinearLayout.VERTICAL);

        for (int row = 0; row < 20; row++) {
            LinearLayout line = new LinearLayout(this);
            line.setOrientation(LinearLayout.HORIZONTAL);
            line.setGravity(Gravity.CENTER);
            for (int col = 0; col < 10; col++) {
                int index = row * 10 + col;
                boolean warm = index % 4 != 3;
                TextView led = new TextView(this);
                led.setText("●");
                led.setTextSize(23);
                led.setGravity(Gravity.CENTER);
                led.setTextColor(warm ? Color.rgb(255, 220, 160) : Color.rgb(220, 235, 255));
                line.addView(led, new LinearLayout.LayoutParams(0, 42, 1));
            }
            grid.addView(line);
        }

        scroll.addView(grid);
        root.addView(scroll, new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 0, 1
        ));
        setContentView(root);
    }
}
