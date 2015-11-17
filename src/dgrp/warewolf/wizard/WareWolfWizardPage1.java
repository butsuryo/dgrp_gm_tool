package dgrp.warewolf.wizard;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import dgrp.StringUtil;
import dgrp.Util;
import dgrp.warewolf.WareWolfCastFactory;
import dgrp.warewolf.model.Cast;
import dgrp.warewolf.model.DifficultyEnum;



/**
 * ゲーム開始設定のウィザードページ1
 * 参加人数、キャラ使用範囲(無印・2)、難易度、配役を決定する
 *
 * @author butsuryo
 *
 */
public class WareWolfWizardPage1 extends WizardPage {

    private WareWolfWizard wizard;

    private Button gmCheckButton;
    private Combo playerNumCombo;
    private org.eclipse.swt.widgets.List difficultyList;
    private org.eclipse.swt.widgets.List castList;
    private org.eclipse.swt.widgets.List characterSelectList;
    private Button randomButton;
    private ArrayList<Cast> castModelList;
    private boolean isFirst;

    /**
     * コンストラクタ
     * @param pageName
     */
    protected WareWolfWizardPage1(String pageName) {
        super(pageName);
        setDescription("参加人数、難易度、配役を選択してください。GMを初日犠牲固定にする場合、参加人数はGMを含めた人数を選択してください。");
        this.isFirst = true;
        setPageComplete(false);
    }

    /**
     * 難易度リストが選択されているかどうか取得する
     * @return boolean 難易度リストが選択されているかどうか
     */
    public boolean isSelectedDifficulty() {
        if (difficultyList.getSelectionIndex() != -1) {
            return true;
        }
        return false;
    }

    /**
     * 現在選択されている配役の配役表示名を取得する
     * @return 現在選択されている配役の配役表示名
     */
    public String getCastName() {
        return this.castList.getItem(castList.getSelectionIndex());
    }

    /**
     * 「GMを↑の参加者に含め、初日犠牲者とする」チェックが入っているかどうか
     * @return boolean 「GMを↑の参加者に含め、初日犠牲者とする」チェックが入っているかどうか
     */
    public boolean isContainGm() {
        return this.gmCheckButton.getSelection();
    }

    /**
     * 現在選択されている参加人数を取得する
     * @return 現在選択されている参加人数
     */
    public String getPlayerNum(){
        return playerNumCombo.getItem(playerNumCombo.getSelectionIndex());
    }

    /**
     * 現在選択されているキャラ使用範囲のインデックスを取得する
     * @return 現在選択されているキャラ使用範囲のインデックス
     */
    public int getCharacterSelect(){
        return characterSelectList.getSelectionIndex();
    }

    /**
     * 現在選択されている難易度の難易度Enumを取得する
     * @return 現在選択されている難易度の難易度Enum
     */
    public DifficultyEnum getDifficulty() {
        String difficultyLabel = difficultyList.getItem(difficultyList.getSelectionIndex());
        return DifficultyEnum.getDifficultyEnumByName(difficultyLabel);
    }

    /**
     * 現在選択されている配役オブジェクトを取得する
     * @return 現在選択されている配役オブジェクト
     */
    public Cast getCastModel() {
        for (Cast cast : castModelList){
            if (cast.getCastName().equals(getCastName())) {
                return cast;
            }
        }
        return null;
    }


    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());

        setTitle("ゲーム開始設定(1/5) -- 配役決定");

        // 人数設定
        createSelectPlayerCountArea(composite);

        // 難易度設定
        createSelectDifficultyArea(composite);

        // 配役設定
        createSelectCastArea(composite);

        setControl(composite);
    }


    /**
     * 人数選択エリア作成
     * @param composite
     */
    private void createSelectPlayerCountArea(Composite inputGroup){

        /** 人数選択リスト **/
        Util.createLabel(inputGroup, "ゲームに参加する人数を選択してください。") ;

        // ドロップダウン(入力不可)
        playerNumCombo = new Combo(inputGroup, SWT.READ_ONLY);
        // リスト中身のセット
        setPlaylerNumber();
        // リスナーセット
        playerNumCombo.addSelectionListener(new PlayerNumAndDifficultySelectionListener(this));


        /** GM初日設定チェックボックス **/
        gmCheckButton = new Button(inputGroup, SWT.CHECK);
        gmCheckButton.setText("GMを↑の参加者に含め、初日犠牲者とする");
        Util.createLabel(inputGroup, StringUtil.EMPTY);


        /** キャラクター選択肢 **/
        Util.createLabel(inputGroup, "ゲームに使用するキャラクターの範囲を選択してください。");
        characterSelectList = new org.eclipse.swt.widgets.List(inputGroup, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
        characterSelectList.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));
        // リスト中身のセット
        characterSelectList.add("1、2両方のキャラを混ぜる");
        characterSelectList.add("ダンガンロンパ1のキャラのみ");
        characterSelectList.add("スーパーダンガンロンパ2のキャラのみ");
        characterSelectList.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                // このページの設定が終わったか判定
                judgePageComplete();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // do nothing.
            }
        });

        Util.createLabel(inputGroup, StringUtil.EMPTY);
    }


    /**
     * 難易度選択エリア作成
     * @param composite
     */
    private void createSelectDifficultyArea(Composite inputGroup) {

        /** 難易度選択リスト **/
        Util.createLabel(inputGroup, "難易度を選択してください。") ;

        difficultyList = new org.eclipse.swt.widgets.List(inputGroup, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
        difficultyList.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));

        // リスナーセット
        difficultyList.addSelectionListener(new PlayerNumAndDifficultySelectionListener(this));

        // 難易度リストはEnumから取得
        EnumSet<DifficultyEnum> allDifficultySet = EnumSet.allOf(DifficultyEnum.class);
        for(DifficultyEnum eachEnum : allDifficultySet ){
            difficultyList.add(eachEnum.difficultyName);
        }
        Util.createLabel(inputGroup, StringUtil.EMPTY);
    }


    /**
     * 配役選択エリア作成
     * @param composite
     */
    private void createSelectCastArea(Composite inputGroup) {

        /** 配役選択リスト **/
        Util.createLabel(inputGroup, "配役を選択してください。複数パターンが存在する場合、「ランダム」ボタンを押すことでランダムで選択することができます。") ;

        castList = new org.eclipse.swt.widgets.List(inputGroup, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
        castList.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));
        castList.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                // このページの設定が終わったか判定
                judgePageComplete();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // do nothing.
            }
        });


        /** ランダムボタン **/
        randomButton = new Button(inputGroup, SWT.PUSH);
        randomButton.setText("ランダム決定");
        randomButton.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                // 現在表示されている配役の選択肢数を取得
                int itemCount = castList.getItemCount();

                // indexをランダム決定
                Random random = new Random();
                int index = random.nextInt(itemCount);

                // 決定したindexを選択状態にする
                castList.setSelection(index);

                // このページの設定が終わったか判定
                judgePageComplete();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                // do nothing.
            }
        });

        // 人数と難易度の両方が選択されるまでは非活性化
        castList.setEnabled(false);
        randomButton.setEnabled(false);
    }


    /**
     * ゲームの参加人数の選択肢を配列で取得する
     * @return ゲームの参加人数の選択肢(文字列配列)
     */
    private ArrayList<String> getPlayerNumber(){

        ArrayList<String> playerNumberArray = new ArrayList<String>();

        // 4人以上16人以下で固定
        for(int i=4; i<=16; i++){
            playerNumberArray.add(Integer.toString(i));
        }

        return playerNumberArray;
    }


    /**
     * プレイヤー人数選択リストに選択肢の人数を表示する
     */
    private void setPlaylerNumber(){

        // 人数をリストに追加
        ArrayList<String> playerNumberArray = getPlayerNumber();

        for(String playerNumber : playerNumberArray){
            playerNumCombo.add(playerNumber);
        }
        playerNumCombo.select(0); // 最初のアイテムを選択状態にする

    }


    /**
     * 配役選択リストに選択肢の配役を表示させる
     */
    public void setCastList(){

        // 選択されている人数を取得
        String playerNum = getPlayerNum();

        // 選択されている難易度を取得
        DifficultyEnum difficulty = getDifficulty();

        // 配役選択リストとランダムボタンを活性化
        castList.setEnabled(true);
        randomButton.setEnabled(true);

        // いったんリストを初期化
        castList.removeAll();

        // 人数に対応する配役を取得
        castModelList = WareWolfCastFactory.createCast(Integer.parseInt(playerNum), difficulty);

        // 配役の表示名をリストに追加
        if (castModelList == null) {
            castList.add("配役が取得できませんでした。Error occured:GameConfigWizardPage1.java #setCastList()");
        } else {
            for(Cast cast : castModelList){
                castList.add(cast.getCastName());
            }
        }
    }


    /**
     * このページの設定が終わったか判定し、完了していたら「次へ」ボタンを活性化させる
     * 「配役」「キャラクター範囲」の2つが選択済みであればOK。
     * （人数、難易度が選択されるまで配役は選択できない）
     */
    private void judgePageComplete() {

        // 配役、キャラクター範囲が両方選択されていたら次のページに進んで良い
        if((castList.getSelectionIndex() != -1) && (characterSelectList.getSelectionIndex() != -1)){
            setPageComplete(true);

            // 親ウィザードに決定事項を保持
            wizard.setCastName(getCastName());
            wizard.setCastModel(getCastModel());
            wizard.setDifficultyEnum(getDifficulty());
            wizard.setPlayerNum(getPlayerNum());
            wizard.setUseCharacterType(getCharacterSelect());

        } else {
            setPageComplete(false);
        }
    }

    /* (非 Javadoc)
     * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
     * ページがアクティブになった時に通る
     */
    @Override
    public void setVisible(boolean visible) {

        // 現在ページと前ページを設定
        wizard = (WareWolfWizard) getWizard();
        if (this.isFirst) {
            wizard.setCurrentPageNumFirst(1);
            this.isFirst = false;
        } else {
            wizard.setCurrentPageNum(1);
        }

        super.setVisible(visible);
    }



}
