package main.java.app;

import main.java.app.util.PrintHelper;

import java.time.LocalDateTime;

public class CoroutineCore implements Runnable{

    private final Object lock_ = new Object();
    private final Object returnLock_ = new Object();
    private Thread thread_;
    private boolean exit_ = false;
    private boolean exitDone_ = false;

    /**
     * スレッドを開始します。
     */
    public void start() {
        System.out.println(PrintHelper.concatExecutedStrings()+"スレッドを開始します");

        thread_ = new Thread(this);
        thread_.start();

        // returnLock_がnotifyを待つ
        synchronized (returnLock_) {
            try {
                returnLock_.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * スレッドを終了します。
     */
    public void end() {
        exit_ = true;
        synchronized (lock_) {
            lock_.notifyAll();
        }
        System.out.println(PrintHelper.concatExecutedStrings()+"スレッドを終了します");
    }

    public boolean isExit() {
        return exit_;
    }

    public boolean isExitDone() {
        return exitDone_;
    }

    @Override
    public void run() {
        // 開始処理、呼び出し元のwaitを解除する
        synchronized (returnLock_){
            returnLock_.notifyAll();
        }

        // nextメソッドが呼ばれるまでwait
        synchronized (lock_) {
            try {
                lock_.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // コルーチン本体の呼び出し
        action();

        // 終了処理
        exitDone_ = true;
        synchronized (returnLock_) {
            returnLock_.notifyAll();
        }
    }

    public void action() {
        System.out.println(PrintHelper.concatExecutedStrings()+"アクションを開始します。");

        // yieldメソッドの戻りがtrueなら、終了要求なのでアクションを終了する
        System.out.println(PrintHelper.concatExecutedStrings() + " POS1");
        if (yield()) return;
        System.out.println(PrintHelper.concatExecutedStrings() + " POS2");
        if (yield()) return;
        System.out.println(PrintHelper.concatExecutedStrings() + " POS3");
        if (yield()) return;
        System.out.println(PrintHelper.concatExecutedStrings() + " POS4");
        if (yield()) return;
        System.out.println(PrintHelper.concatExecutedStrings() + " POS5");
        if (yield()) return;

        if (yield()) return;

        System.out.println(PrintHelper.concatExecutedStrings()+"アクションを終了します。");
    }

    /**
     * コルーチンの処理を中断して元の処理に戻る
     * @return コルーチン終了要求
     */
    public boolean yield() {

        synchronized (returnLock_) {
            returnLock_.notifyAll();
        }
        try {
            synchronized (lock_) {
                lock_.wait(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return exit_;
    }

    public boolean next() {
        if (exit_ || exitDone_) return false;
        try {
            synchronized (returnLock_) {
                synchronized (lock_) {
                    lock_.notifyAll();
                }
                // コルーチン内でyieldが呼ばれるまで待機
                returnLock_.wait();
            }
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
