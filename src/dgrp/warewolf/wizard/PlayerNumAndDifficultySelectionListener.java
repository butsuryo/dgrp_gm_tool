package dgrp.warewolf.wizard;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * 1ページ目のウィザードページで「人数」もしくは「難易度」のコンボが選択された時のリスナ
 *
 * @author butsuryo
 *
 */
public class PlayerNumAndDifficultySelectionListener implements SelectionListener{

    WareWolfWizardPage1 page;

    public PlayerNumAndDifficultySelectionListener(WareWolfWizardPage1 page) {
        this.page = page;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {

        // もし難易度が選択されていたら、配役表示作成して切り替える
        // (人数は初期状態で既に4人が選択されており、値なしがあり得ない)
        if (page.isSelectedDifficulty()) {
            page.setCastList();
            page.setPageComplete(false);
        }

    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // do nothing.
    }

}
