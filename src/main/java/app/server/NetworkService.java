package main.java.app.server;

import main.java.app.util.PrintHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ソケット通信のエントリーポイントとなるクラス
 */
public class NetworkService implements Runnable {

    private final ServerSocket serverSocket;
    private final ExecutorService executorService;

    /**
     * デフォルトコンストラクタ
     * @param port ネットワークポート
     * @param poolSize スレッドプールのサイズ
     * @throws IOException
     */
    public NetworkService(int port, int poolSize) throws IOException {
        serverSocket = new ServerSocket(port);
        executorService = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void run() {
        System.out.println(PrintHelper.concatExecutedStrings() + "スレッドプールを開始します。");

        try {
            executorService.submit(new ApplicationController((serverSocket.accept())));
        } catch (Exception e) {
            System.out.println(PrintHelper.concatExecutedStrings() + "スレッドの処理中に例外が発生しました。");
            executorService.shutdown();
        }

        System.out.println(PrintHelper.concatExecutedStrings() + "スレッドプールを終了します。");
    }
}
