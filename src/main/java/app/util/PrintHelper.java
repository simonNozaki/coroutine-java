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

    /**
     * 標準出力用メッセージ情報を、実行情報と連結して返します。
     * @param message ログメッセージ
     * @return 実行情報
     */
    public static String concatExecutedStrings(String message){
        // メッセージがない場合はランタイム情報のみ表示
        if (message == null || message.isEmpty()) return concatExecutedStrings();

        return
                LocalDateTime.now() +
                        "["+Thread.currentThread().getName()+"]"
                        +new Throwable().getStackTrace()[1].getClassName()+"#"+new Throwable().getStackTrace()[1].getMethodName()+
                        " " +
                        message;
    }


    public static String logStart() {
        return ">>> start <<<";
    }

    public static String logEnd() {
        return ">>> end <<<";
    }
}
