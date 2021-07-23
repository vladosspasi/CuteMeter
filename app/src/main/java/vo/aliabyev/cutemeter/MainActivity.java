package vo.aliabyev.cutemeter;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import vo.aliabyev.cutemeter.ui.main.PageViewModel;
import vo.aliabyev.cutemeter.ui.main.SectionsPagerAdapter;

/**
 * Главная активность приложения, в которой создаются фрагменты
 */
public class MainActivity extends AppCompatActivity{

    Integer level = 0;
    PageViewModel pageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        pageViewModel.setLevel(level);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            if(level <15){
                level++;
                pageViewModel.setLevel(level);
                Log.d("УРОВЕНЬ В АКТИВИТИ",level.toString());
            }
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if(level>0){
                level--;
                pageViewModel.setLevel(level);
                Log.d("УРОВЕНЬ В АКТИВИТИ",level.toString());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}