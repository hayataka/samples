// Type definitions for tetris

///<reference path="../jquery/jquery.d.ts" />

/**
 * tconf
 */

interface Config {
    /**
     * @return ゲーム盤面の幅（セル数）（当たり判定込み）を返却します。
     */
    w(): number;
    
    /**
     * @return ゲーム盤面の高さ（セル数）（当たり判定込み）を返却します。
     */
    h():number;
    /**
     * @return 画面再描画速度（ms）を返却します。
     */
    ms(): number;
    /**
     * 縦に１セル動くまでに、何回move関数を実行するかを定義する.
     */
    s():number;
    /**
     * settimeout関数に渡す、再度move関数を呼び出すまでの時間を定義
     */
    interval() : number;
    /**
     * webSocket再接続試行までの待機秒数 
     */
    reConnect():number;
    /**
     * 通信圧縮から元の色名に戻すためのmap
     */
    blockDecompress() : any;

    blocks():any;
    u():string;
    
}

interface Game {
	/**
	 * ゲーム盤面となるtrタグ、tdタグの文字列情報を返却する.
	 * ゲーム開始ボタンを押す前までに、事前状況を作っておく
	 * @return tr や tdタグを、configで設定した分の文字列情報
	 */	   
    preparePad() :any;
	/**
	 * keyboardイベントを変数に格納しておく.
	 */    
    keyboardEvent():any;
    
	/**
	 * げーむすたーとボタンを押した後に、最初に動く挙動を定義する.
	 */
	gameInitial():any;
}

interface Tetris {
    /**
     * 設定情報(主に定数）の返却
     */
    config: Config;
    /**
     * ゲームの挙動を司る処理
     */
    game: Game;
    
}

 declare module "tetris" {
    export = t;

 }
 declare var t: Tetris;