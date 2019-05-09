package cn.ddnd.yohttp.pipeline;

import cn.ddnd.yohttp.Request;
import cn.ddnd.yohttp.Response;

import java.io.*;
import java.util.List;

public final class CallServerInterceptor implements Interceptor{
    private final Request request;

    public CallServerInterceptor(Request request) {
        this.request = request;
    }

    @Override
    public Response proceed(Response response) {
        OutputStreamWriter outputStream;
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

            byte[] var5 = new byte[1500];
            int var6 = request.getConnection().getSocket().getInputStream().read(var5);
            int headEnd = 0;

            for (int i = 0; i < var6; i++) {  // \r\n \r\n
                if ((Byte.toString(var5[i]).toLowerCase()).equals("10"))
                    if ((Byte.toString(var5[i-1]).toLowerCase()).equals("13"))
                        if ((Byte.toString(var5[i-2]).toLowerCase()).equals("10"))
                            if ((Byte.toString(var5[i-3]).toLowerCase()).equals("13")) {
                                byte[] var7 = new byte[i];
                                System.arraycopy(var5, 0, var7, 0, i);
                                response.setHeaderInfo(new String(var7, request.getCharSet()));
                                headEnd = i;
                                break;
                            }
            }

            byte[] var8 = new byte[var6 - headEnd];
            System.arraycopy(var5, headEnd, var8, 0, var6 - headEnd);
            response.setBody(new String(var8, request.getCharSet()));

            if (response.getBody().charAt(response.getBody().length() - 1) == '\n'
                    && response.getBody().charAt(response.getBody().length() - 2) == '\r'
                    && response.getBody().charAt(response.getBody().length() - 3) == '\n'
                    && response.getBody().charAt(response.getBody().length() - 4) == '\r'
                    && response.getBody().charAt(response.getBody().length() - 5) == '0')
                return response;

            if (response.getHeaderInfo() != null && response.getHeaderInfo().contains("chunked")) { //chunked type
                int var10 = 2;
                byte[] var9 = new byte[2000];
                var9[0] = 1;
                while (!Byte.toString(var9[var10]).toLowerCase().equals("10")
                        && !Byte.toString(var9[var10 - 1]).toLowerCase().equals("13")
                        && !Byte.toString(var9[var10 - 2]).toLowerCase().equals("0")) {
                    var10 = request.getConnection().getSocket().getInputStream().read(var9);
                    byte[] var11 = new byte[var10];
                    System.arraycopy(var9, 0, var11, 0, var10);
                    var10--;
                    response.setBody(response.getBody() + new String(var11, request.getCharSet()));
                }
            }

            if (response.getHeaderInfo() != null && response.getHeaderInfo().contains("Content-Length:")) { //Content-Length type
                int var12 = response.getHeaderInfo().indexOf("Content-Length:");
                String var13 = "";
                while (response.getHeaderInfo().charAt(var12) != '\r') {
                    var13 += response.getHeaderInfo().charAt(var12);
                    var12++;
                }
                var13 = var13.replaceAll("Content-Length:", "").replaceAll(" ","");
                int var14 = Integer.valueOf(var13);
                var14 -= var8.length;
                int fail = 0;
                while (var14 > 0) {
                    byte[] var15  = new byte[3000];
                    if (request.getConnection().getSocket().getInputStream().available() <= 0) {
                        fail++;
                    }
                    if (fail > 10)
                        break;
                    int var16 = request.getConnection().getSocket().getInputStream().read(var15);
                    var14 -= var16;
                    byte[] var17 = new byte[var16];
                    System.arraycopy(var15, 0, var17, 0, var16);
                    response.setBody(response.getBody() + new String(var17, request.getCharSet()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
