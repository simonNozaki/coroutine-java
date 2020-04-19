package main.java.app.server;

import main.java.app.util.PrintHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * サーブレットコントローラクラス。
 */
@WebServlet(name = "servlet", value = "/index")
public class ServletController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(PrintHelper.concatExecutedStrings("サーブレットを開始します。"));

        try {
            HttpRequest httpRequest = new HttpRequest(req.getInputStream(), resp.getOutputStream());
            httpRequest.peekRequest();
            httpRequest.writeHtmml();
        } catch (Exception e) {
            System.out.println(PrintHelper.concatExecutedStrings("サーブレットの処理中にシステムエラーが発生しました。"));
        }

        System.out.println(PrintHelper.concatExecutedStrings("サーブレットを終了します。"));
    }

}
