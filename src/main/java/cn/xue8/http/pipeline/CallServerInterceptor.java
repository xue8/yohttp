package cn.xue8.http.pipeline;

import cn.xue8.http.Request;
import cn.xue8.http.Response;

import java.io.*;
import java.util.List;

/**
 * @program: http
 * @description:
 * @author: Xue 8
 * @create: 2019-04-07 21:08
 **/
public final class CallServerInterceptor implements Interceptor{
    private final Request request;

    public CallServerInterceptor(Request request) {
        this.request = request;
    }

    @Override
    public Response proceed(Response response) {
        OutputStreamWriter outputStream;
        BufferedInputStream bufferedInputStream;
        BufferedReader bufferedReader;
        try {
            List<List<String>> headers = request.getHeaders().getNameAndValues();
            outputStream = new OutputStreamWriter(request.getConnection().getSocket().getOutputStream());
            String out = "";
            for (int i = 0; i < headers.size(); i++) {
                out += headers.get(i).get(0) + headers.get(i).get(1) + "\r\n";
            }
            out += "\r\n";
            if (request.getRequestBody() != null)
                out += request.getRequestBody();
            outputStream.write(out);
            outputStream.flush();

            bufferedInputStream = new BufferedInputStream(request.getConnection().getSocket().getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "utf-8"));
            String var1 = null;
            boolean chunked = false;
            int contentLenght;
            while ((var1 = bufferedReader.readLine()) != null && !var1.equals("")) {
                response.setHeaderInfo(response.getHeaderInfo() + var1 + "\r\n");
                if (var1.contains("chunked"))
                    chunked = true;
                if (var1.contains("Content-Length"))
                    contentLenght = Integer.valueOf(var1.replace("Content-Length", ""));
            }
            String var2 = null;
            if (chunked) {
                String var3 = null; //pre line
                while ((var2 = bufferedReader.readLine()) != null) {
                    if (var2.equals("") && var3.equals("0"))
                        break;
                    response.setBody(response.getBody() + var2 + "\r\n");
                    var3 = var2;
                }
            } else {
                throw new IllegalStateException("error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
