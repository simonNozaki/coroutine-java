package main.java.app.server;

import main.java.app.util.PrintHelper;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * HTTPリクエスト関連クラス
 */
public class HttpRequest {

    /**
     * ヘッダー文字列
     */
    private final String headerText;

    /**
     * HTTPボディ文字列
     */
    private final String bodyText;

    /**
     * バッファライター
     */
    private BufferedWriter bufferedWriter;

    private int contentLength = 0;

    /**
     * デフォルトコンストラクタ
     * @param inputStream 任意の読み込みデータストリーム
     * @param outputStream 任意の書き出しデータストリーム
     * @throws IOException
     */
    public HttpRequest(InputStream inputStream, OutputStream outputStream) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        headerText = this.readHeader(bufferedReader);
        bodyText = this.readBody(bufferedReader);

    }

    /**
     * HTTPリクエストのヘッダーを読み込みます
     * @param bufferedReader ソケットから受け取ったバッファリーダー
     * @return ヘッダー文字列
     * @throws IOException
     */
    private String readHeader(BufferedReader bufferedReader) throws IOException {

        String line = bufferedReader.readLine();
        final StringBuilder header = new StringBuilder();

        while (line != null && !line.isEmpty()) {
            // Content-Lengthのカウント
            if (line.startsWith("Content-Length")) contentLength = Integer.parseInt(line.split(":")[1].trim());
            header.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        return header.toString();
    }

    /**
     * HTTPリクエストのボディ部を読み込みます。
     * @param bufferedReader ソケットから受け取ったバッファリーダー
     * @return HTTPボディ
     * @throws IOException
     */
    private String readBody(BufferedReader bufferedReader) throws IOException {
        // HTTPリクエストボディの読み込み
        char[] characters = new char[contentLength];
        bufferedReader.read(characters);

        return new String(characters);
    }

    public String getHeaderText() { return this.headerText; }

    public String getBodyText() { return this.bodyText; }

    /**
     * リクエスト内容を標準出力します。
     */
    public void peekRequest() {
        System.out.println(PrintHelper.logStart() + "\n" + getHeaderText() + "\n" + getBodyText() + "\n" + PrintHelper.logEnd());
    }

    /**
     * 200 OKをクライアントに返却します。HTTPボディなし。
     * @throws IOException
     */
    public void statusOk() throws IOException {
        System.out.println(PrintHelper.concatExecutedStrings() + "HTTPレスポンスを返却します。");
        bufferedWriter.write("HTTP/1.1 200 OK\r\n");
    }

    /**
     * 簡易htmlを返します。
     * @throws IOException
     */
    public void writeHtmml() throws IOException {
        System.out.println(PrintHelper.concatExecutedStrings("HTTPレスポンスを返却します。"));
        bufferedWriter.write("HTTP/1.1 200 OK\n");
        bufferedWriter.write("Content-Type: text/html\n");
        bufferedWriter.write("\n");
        bufferedWriter.write(defineTopHtml());
    }

    private String defineTopHtml() {
        return "<div>" +
                "<h1> Servlet Container </h1>" +
                "<p> サーブレットが書き出したHTMLです </p>" +
                "</div>";
    }
}
