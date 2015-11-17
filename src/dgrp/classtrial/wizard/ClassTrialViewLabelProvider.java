package dgrp.classtrial.wizard;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import dgrp.StringUtil;
import dgrp.Util;
import dgrp.classtrial.model.Player;

/**
 * 参加者設定確認ビューのラベルプロバイダ
 * @author butsuryo
 *
 */
public class ClassTrialViewLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {

    public String getColumnText(Object obj, int columnIndex) {

        if(!(obj instanceof Player)){
            return StringUtil.EMPTY;
        }

        Player data = (Player)obj;

        switch(columnIndex){

            // 参加者名
            case 0:
                return data.getPlayerName();

            // キャラクター名
            case 1:
                return data.getCharacter().getCharacterName();

            // 役職 (交換後役職があればそちらを優先)
            case 2:
                return data.getCurrentRole().roleName;

            // 交換前役職
            case 3:
                // 交換後役職がセットされている場合のみ、元役職を返す
                if (data.getAfterTradeRole() != null) {
                    return data.getDefaultRole().roleName;
                }

        }
        return StringUtil.EMPTY;
    }

    public Image getColumnImage(Object obj, int index) {
        return null;
    }

    @Override
    public Color getForeground(Object obj, int columnIndex) {
        // no operation
        return null;
    }

    @Override
    public Color getBackground(Object obj, int columnIndex) {

        if (!(obj instanceof Player)) {
            return null;
        }

        Player data = (Player)obj;

        // 役職を交換した場合、役職、交換前役職のカラムの背景色をピンクに変更する
        if (data.getAfterTradeRole() != null) {
            if (columnIndex == 2 || columnIndex == 3) {
                return Util.pink;
            }
        }
        return null;
    }


}
