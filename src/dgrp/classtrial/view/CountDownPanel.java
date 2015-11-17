package dgrp.classtrial.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CountDownPanel extends JPanel implements Runnable{


    //設定秒数
    private int setSec;

    //残りの秒数
    private int secLeft;

    //パネルのサイズ
    public static final int WIDTH = 250;
    public static final int HEIGHT = 50;

    //秒数ラベル
    private JLabel label = new JLabel();


    /**
     * コンストラクタ
     */
    CountDownPanel(int n){

        //初期化処理
        this.setSec = n;
        this.secLeft = n;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());

        //GUI部品の配置
        JPanel p = new JPanel();
        p.add(label);
        add(p);

        //スレッドの生成
        Thread thread = new Thread(this);
        thread.start();

    }

    /**
     * runメソッド
     */
    public void run(){

        // 0秒になるまでループ
        while(secLeft>0){

            // 1秒ごとにラベルを更新
            if(secLeft%1==0){
                label.setText("残り" + Integer.toString(secLeft) + "秒（" + String.valueOf(secLeft / 60) + "分" + String.valueOf(secLeft % 60)  + "秒）");
            }
            wait(1); //1秒待つ
            secLeft-=1; //1秒引く

            if (!getParent().isShowing()) {
                break;
            }
        }

        // 閉じられてない時だけダイアログを表示
        if (getParent().isShowing()) {
            label.setText("残り" + Integer.toString(secLeft) + "秒（" + String.valueOf(secLeft / 60) + "分" + String.valueOf(secLeft % 60)  + "秒）");
            JOptionPane.showMessageDialog(this, setSec / 60 + "分経ちました。", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     * time秒間スリープするメソッド
     */
    public void wait(int time){
        try{
            Thread.sleep(time*1000);
        }catch(InterruptedException e){
            System.out.println(e);
        }
    }
}
