package dgrp.warewolf.wizard;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import dgrp.StringUtil;
import dgrp.Util;
import dgrp.warewolf.model.Player;


public class WareWolfViewLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {


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

            // 役職
            case 2:
                return data.getRole().roleName;

            // アイテム1
            case 3:
                if (data.getItem1() == null) {
                    return StringUtil.EMPTY;
                }
                return data.getItem1().getItemName();

            // アイテム2
            case 4:
                if (data.getItem2() == null) {
                    return StringUtil.EMPTY;
                }
                return data.getItem2().getItemName();

            // 引き直し済み
            case 5:
                if (data.isItemRedraw()) {
                    return "済";
                }
        }

        return StringUtil.EMPTY;
    }

    public Image getColumnImage(Object obj, int index) {
        return null;
    }

    @Override
    public Color getForeground(Object obj, int columnIndex) {
        return null;
    }

    @Override
    public Color getBackground(Object obj, int columnIndex) {

        if(!(obj instanceof Player)){
            return null;
        }

        Player data = (Player)obj;

        if (data.isItemRedraw()) {
            return Util.pink;
        }

        return null;
    }


}
