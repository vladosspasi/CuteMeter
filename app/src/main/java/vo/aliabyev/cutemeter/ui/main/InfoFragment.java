package vo.aliabyev.cutemeter.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import vo.aliabyev.cutemeter.R;

/** Фрагмент информации с информацией*/
public class InfoFragment extends Fragment {

    public static InfoFragment newInstance(/*int index*/) {
        return new InfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }
}