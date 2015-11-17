package dgrp.warewolf.view;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.ViewPart;

import dgrp.Activator;
import dgrp.StringUtil;
import dgrp.Util;
import dgrp.warewolf.model.GameData;

public class WareWolfStatusView extends ViewPart {
    public static final String ID = "statusview";

    private Composite parent;
    private Composite composite;

    /**
     * This is a callback that will allow us to create the viewer and initialize it.
     */
    public void createPartControl(Composite parent) {

        this.parent = parent;
        this.composite = Util.createGridComposite(parent, 0);

        GameData gameData = Activator.getGameData();

        // TODO データきれいにする GameDataとれないときは「ゲームデータがありません」でいいんじゃない？
        int day = 0;
        String timeZone = StringUtil.EMPTY;
        String attackSuccess = StringUtil.EMPTY;
        int victimCnt = 0;
        int targetVictimCnt = 0;
        String attackTarget = StringUtil.EMPTY;
        String egoTarget = StringUtil.EMPTY;
        String egoResult = StringUtil.EMPTY;
        String deadCharacter = StringUtil.EMPTY;

        if (gameData != null) {
            day = gameData.getDay();
            timeZone = gameData.getCurrentTimeZone().timeZoneLabel;
            attackSuccess = (gameData.isAttackSuccess()) ? "（成功）" : "（失敗）";
            victimCnt = gameData.getVictimCnt();
            targetVictimCnt = gameData.getCast().getTargetVictimCount();

            if (gameData.getAttackTarget() != null) {
                attackTarget = gameData.getAttackTarget().getCharacter().getCharacterName();
            }

            if (gameData.getEgoTarget() != null) {
                egoTarget = gameData.getEgoTarget().getCharacter().getCharacterName();
                egoResult = (gameData.isEgoResultBlack()) ? "●" : "○";
            }
            deadCharacter = (gameData.isAttackSuccess()) ? attackTarget : "なし";
        }

        FormToolkit toolkit = new FormToolkit(composite.getDisplay());
        Form form = toolkit.createForm(composite);
        form.setLayoutData(Util.createFillBothGridData());

        // フォーム本体のレイアウトを設定
        Composite body = form.getBody();
        body.setLayout(new GridLayout(3, false));


        toolkit.createLabel(body, "日にち");
        toolkit.createLabel(body, "：");
        toolkit.createLabel(body, day + "日目");

        toolkit.createLabel(body, "時間帯");
        toolkit.createLabel(body, "：");
        toolkit.createLabel(body, timeZone);

        toolkit.createLabel(body, "殺害数");
        toolkit.createLabel(body, "：");
        toolkit.createLabel(body, victimCnt + "/" + targetVictimCnt);

        toolkit.createLabel(body, "死体");
        toolkit.createLabel(body, "：");
        toolkit.createLabel(body, deadCharacter);

        toolkit.createLabel(body, "おしおき");
        toolkit.createLabel(body, "：");
        toolkit.createLabel(body, "－");

        toolkit.createLabel(body, "襲撃先");
        toolkit.createLabel(body, "：");
        toolkit.createLabel(body, attackTarget + attackSuccess);

        toolkit.createLabel(body, "調査先");
        toolkit.createLabel(body, "：");
        toolkit.createLabel(body, egoTarget + "（" + egoResult + "）");


//        ・襲撃先/結果(朝～深夜ずっと)深夜の実行後のみ文字色を赤にする
//        ・調査先/結果(朝～深夜ずっと)深夜の実行後のみ文字色を赤にする

    }

    private void updateView() {

        // 画面の更新
        this.composite.dispose();
        this.createPartControl(this.parent);
        this.parent.layout();
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus() {
    }
}