package vo.aliabyev.cutemeter.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import org.jetbrains.annotations.NotNull;
import vo.aliabyev.cutemeter.R;

/**
 * Адаптер для отображения вкладок
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    @NotNull
    public Fragment getItem(int position) {
        switch (position){
            case 0: {
                return MainFragment.newInstance();
            }
            case 1: {
                return SettingsFragment.newInstance();
            }
            case 2: {
                return InfoFragment.newInstance();
            }
        }
        return new Fragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 3;
    }
}