package main.java.app.util;

import java.time.LocalDateTime;

public class PrintHelper {

    /**
     * デフォルトコンストラクタ、不可視
     */
    private PrintHelper(){}

    /**
     * 標準出力用実行メソッド情報を返します。
     * @return
     */
    public static String concatExecutedStrings(){
        return
                LocalDateTime.now() +
                "["+Thread.currentThread().getName()+"]"
                +new Throwable().getStackTrace()[1].getClassName()+"#"+new Throwable().getStackTrace()[1].getMethodName()+
                " ";
    }

    public static String logStart() {
        return ">>> start <<<";
    }

    public static String logEnd() {
        return ">>> end <<<";
    }
}
