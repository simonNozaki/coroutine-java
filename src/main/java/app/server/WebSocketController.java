package main.java.app.server;

import main.java.app.util.PrintHelper;
import java.net.Socket;

/**
 * ソケットに対応するコントローラクラス
 */
public class WebSocketController implements Runnable {

    private final Socket socket;

    /**
     * デフォルトコンストラクタ
     * @param socket ソケット
     */
    WebSocketController(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        System.out.println(PrintHelper.concatExecutedStrings() + "Controllerを開始します。");

        try {
            // HTTPリクエスト、レスポンスをバッファリング
            final HttpRequest httpRequest = new HttpRequest(socket.getInputStream(), socket.getOutputStream());
            httpRequest.peekRequest();
            httpRequest.writeHtmml();

        } catch (Exception e) {
            System.out.println(PrintHelper.concatExecutedStrings() + "Controllerの処理中に例外が発生しました。");
            e.printStackTrace();
        }

        System.out.println(PrintHelper.concatExecutedStrings() + "Controllerを終了します。");
    }
}
