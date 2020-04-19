package main.java.app;


import main.java.app.server.NetworkService;
import main.java.app.util.PrintHelper;

public class Main {

    public static void main(String[] args) {
        CoroutineCore coroutineCore = new CoroutineCore();

        try {
            NetworkService networkService = new NetworkService(3000, 5);
            networkService.run();
        } catch (Exception e) {
            System.out.println(PrintHelper.concatExecutedStrings() + "スレッドの処理中に例外が発生しました。");
            e.printStackTrace();
        }

    }

}
