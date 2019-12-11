package apertx.tuturu;

import android.app.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;

public class MainActivity extends Activity {
	SharedPreferences settings;
	TextView box;
	SoundPool sound;
	long count;
	boolean theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		settings = getSharedPreferences("settings", this.MODE_PRIVATE);
		theme = settings.getBoolean("theme", false);
		if (theme)
			setTheme(android.R.style.Theme_DeviceDefault_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		box = findViewById(R.id.box);
		sound = new SoundPool(1, 3, 0);
		sound.load(this, R.raw.tuturu, 1);
    }

	@Override
	protected void onResume() {
		count = settings.getLong("count", 0);
		box.setText("" + count);
		super.onResume();
	}

	@Override
	protected void onPause() {
		settings.edit().putLong("count", count).apply();
		super.onPause();
	}

	public void click(View view) {
		count += 1;
		sound.play(1, 1, 1, 1, 0, 1);
		box.setText("" + count);
	}

	public void reset(View view) {
		new AlertDialog.Builder(this)
			.setTitle("Do you want to reset your score?")
			.setMessage("It will restart your progress").
			setNegativeButton("NO", null).
			setPositiveButton("YES", new AlertDialog.OnClickListener() {
				@Override
				public void onClick(DialogInterface p1, int p2) {
					count = 0;
					box.setText("" + count);
					settings.edit().putBoolean("theme", !theme).apply();
				}
			}).show();
	}
}
