package vo.aliabyev.cutemeter.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONObject;
import vo.aliabyev.cutemeter.R;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

/** Фрагмент настроек с опциями*/
public class SettingsFragment extends Fragment{

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }
    private final String FILE_NAME = "config.json";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        view.findViewById(R.id.save_button).setOnClickListener(v -> onSaveSettingsClick(view));
        setSavedValues(view);
        return view;
    }

    public void onSaveSettingsClick(View view){
        FileOutputStream fos = null;
        Context context = getContext();
        JSONObject configList = new JSONObject();
        try {
            //Считывание параметров и запись их в JSON-файл
            Switch option1 = view.findViewById(R.id.option_switch1);
            Switch option2 = view.findViewById(R.id.option_switch2);
            Switch option3 = view.findViewById(R.id.option_switch3);
            Switch option4 = view.findViewById(R.id.option_switch4);
            SeekBar option5 = view.findViewById(R.id.seekBar_option5);
            SeekBar option6 = view.findViewById(R.id.seekBar_option6);
            SeekBar option7 = view.findViewById(R.id.seekBar_option7);
            if(option1.isChecked()){
                configList.put("option1", "true");
            }else{
                configList.put("option1", "false");
            }
            if(option2.isChecked()){
                configList.put("option2", "true");
            }else{
                configList.put("option2", "false");
            }
            if(option3.isChecked()){
                configList.put("option3", "true");
            }else{
                configList.put("option3", "false");
            }
            if(option4.isChecked()){
                configList.put("option4", "true");
            }else{
                configList.put("option4", "false");
            }
            configList.put("option5", String.valueOf(option5.getProgress()));
            configList.put("option6", String.valueOf(option6.getProgress()));
            configList.put("option7", String.valueOf(option7.getProgress()));
            if(context!=null){
                fos = context.openFileOutput(FILE_NAME, MODE_PRIVATE);
            }else{
                throw new Exception("Невозможно открыть файл");
            }
            fos.write(configList.toString().getBytes());
        }catch (Exception e) {
            Log.d("Ошибка:", e.toString());
            return;
        }
        finally {
            try {
                if (fos != null)
                    fos.close();
            }catch (Exception e) {
                Log.d("Ошибка:", e.toString());
                return;
            }
        }
        Log.d("Config: ", configList.toString());
        Snackbar.make(view, R.string.settings_saved, Snackbar.LENGTH_LONG).show();
    }

    private void setSavedValues(View view){
        FileInputStream fin = null;
        String JsonString;
        Context context = getContext();
        try {
            assert context != null;
            fin = context.openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            JsonString = new String(bytes);
        } catch (Exception e) {
            Log.d("Ошибка:", e.toString());
            return;
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (Exception e) {
                Log.d("Ошибка:", e.toString());
                return;
            }
        }
        try{
            JSONObject options = new JSONObject(JsonString);
            Switch option1 = view.findViewById(R.id.option_switch1);
            Switch option2 = view.findViewById(R.id.option_switch2);
            Switch option3 = view.findViewById(R.id.option_switch3);
            Switch option4 = view.findViewById(R.id.option_switch4);
            SeekBar option5 = view.findViewById(R.id.seekBar_option5);
            SeekBar option6 = view.findViewById(R.id.seekBar_option6);
            SeekBar option7 = view.findViewById(R.id.seekBar_option7);
            option1.setChecked(options.getBoolean("option1"));
            option2.setChecked(options.getBoolean("option2"));
            option3.setChecked(options.getBoolean("option3"));
            option4.setChecked(options.getBoolean("option4"));
            option5.setProgress(options.getInt("option5"));
            option6.setProgress(options.getInt("option6"));
            option7.setProgress(options.getInt("option7"));
        }catch (Exception e) {
            Log.d("Ошибка: ", e.toString());
        }
    }
}