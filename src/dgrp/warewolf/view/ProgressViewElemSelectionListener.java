package dgrp.warewolf.view;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 *
 *
 * @author butsuryo
 *
 */
public class ProgressViewElemSelectionListener implements SelectionListener{

    private WareWolfProgressionView view;


    public ProgressViewElemSelectionListener(WareWolfProgressionView view) {
        this.view = view;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {

        // ページ完了判定
        view.judgeComplete();

    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // do nothing.
    }

}
