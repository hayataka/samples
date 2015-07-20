// Type definitions for tetris

///<reference path="../jquery/jquery.d.ts" />

/**
 * tconf
 */

declare class TetrisConfig {
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
    interval : number;
    /**
     * webSocket再接続試行までの待機秒数 
     */
    reConnect:number;
    /**
     * 通信圧縮から元の色名に戻すためのmap
     */
    blockDecompress : any;

    blocks:any;
    u:string;
    
}

declare class Tetris {
    /**
     * 設定情報の返却
     */
    config: TetrisConfig;
    
}

declare module "tetris" {
    export = tetris;
}
declare var tetris:Tetris