package dgrp.classtrial.view;
import java.awt.Container;

import javax.swing.JFrame;

public class CountDownPanelContainer extends JFrame{

    //コンテナー
    private Container con;

    /**
     * コンストラクタ
     */
    public CountDownPanelContainer(int n){
        con = this.getContentPane();
        con.add(new CountDownPanel(n));
        pack(); //フレームのサイズを適切にする
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("タイマー"); //タイトルの設定
        setLocationRelativeTo(null); //画面の中央に表示
        setResizable(false); //フレームサイズ変更禁止
        setAlwaysOnTop(true);
        setVisible(true); //表示
    }

}


