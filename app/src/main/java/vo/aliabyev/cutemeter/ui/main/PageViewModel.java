package vo.aliabyev.cutemeter.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Модель, через которую осуществляется передача данных
 * между активностью и фрагментов (передача уровня).
 */
public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mLevel = new MutableLiveData<>();

    public void setLevel(int level) {
        mLevel.setValue(level);
    }

    public Integer getLevel() {
        return mLevel.getValue();
    }

}