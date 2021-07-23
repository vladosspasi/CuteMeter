package vo.aliabyev.cutemeter.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONObject;
import vo.aliabyev.cutemeter.R;
import java.io.FileInputStream;
import java.util.Objects;

/** Основной фрагмент с замером пусечности*/
public class MainFragment extends Fragment {

    PageViewModel pageViewModel;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(requireActivity()).get(PageViewModel.class);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        view.findViewById(R.id.MainButton).setOnClickListener(v -> setCuteLevel(view));
        return view;
    }

    //Получение уровня из активити
    private int getLevel(){
        return pageViewModel.getLevel();
    }

    //Установка и вывод измеренного уровня
    public void setCuteLevel(View view){
        int level;
        if(!areSettingsCorrect(view)){
            level = 15;
        }else{
            level = getLevel();
        }
        String msg;
        switch (level){
            case 0:
                msg = "Уровень неотличим от фонового.";
                break;
            case 11:
                msg = "Значение вне пределов шкалы. Высокая пусечность.";
                break;
            case 12:
                msg = "Значение вне пределов шкалы. Отрицательная пусечность.";
                break;
            case 13:
                msg = "Уберите устройство в безопасное место! Уровень слишком высок!";
                break;
            case 14:
                msg = "Зарегистрирован высокий пусечный фон. Измерения невозможны.";
                break;
            case 15:
                msg = "Ошибка: некорректные настройки.";
                break;
            default:
                msg = "Успешно измерено.";
                break;
        }
        TextView status = view.findViewById(R.id.statusView);
        ProgressBar progress = view.findViewById(R.id.progressBar);
        TextView result = view.findViewById(R.id.Result);
        status.setText(msg);
        String resultText = "Результат: ";
        if(level<=10){
            progress.setProgress(level*10);
            resultText = resultText.concat(level + " МПу");
        }else if(level == 11 || level == 13 || level == 14){
            resultText = resultText.concat("ОШИБКА");
            progress.setProgress(100);
        }else if(level == 12 || level == 15){
            resultText = resultText.concat("ОШИБКА");
            progress.setProgress(0);
        }
        result.setText(resultText);
    }

    //проверка, верны ли настройки
    private boolean areSettingsCorrect(View view){
        FileInputStream fin = null;
        String JsonString;
        Context context = getContext();
        boolean option4;
        int option5;
        int option6;
        int option7;
        try {
            assert context != null;
            String FILE_NAME = "config.json";
            fin = context.openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            JsonString = new String(bytes);
        } catch (Exception e) {
            Snackbar.make(view, R.string.error, Snackbar.LENGTH_LONG).show();
            Log.d("Ошибка:", e.toString());
            return false;
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (Exception e) {
                Snackbar.make(view, R.string.error, Snackbar.LENGTH_LONG).show();
                Log.d("Ошибка:", e.toString());
                return false;
            }
        }
        try{
            JSONObject options = new JSONObject(JsonString);
            option4 = options.getBoolean("option4");
            option5 = options.getInt("option5");
            option6 = options.getInt("option6");
            option7 = options.getInt("option7");
        }catch (Exception e) {
            Snackbar.make(Objects.requireNonNull(getView()), R.string.error, Snackbar.LENGTH_LONG).show();
            Log.d("Ошибка: ", e.toString());
            return false;
        }
        //проверка, что настройки верны
        int check = option5 + option6 + option7;
        return check == 20 && option4;
    }
}