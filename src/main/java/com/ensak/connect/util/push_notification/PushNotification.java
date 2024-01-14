package com.ensak.connect.util.push_notification;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PushNotification {

    public static void sendChatNotificationToUser(
            String title, String content, int senderId, int receiverId, int conversationId) {
        try {
            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic MTIyOTI2OWItZTU2Yy00Y2FjLWI2MzktMjNmZjlmMGU1ZDMx");
            con.setRequestMethod("POST");

            String strJsonBody = "{" +
                    "    \"app_id\": \"33340ee8-8b07-4a12-ad59-36f93ba2402b\"," +
                    "    \"include_aliases\": {" +
                    "        \"external_id\": [" +
                    "            \"" + receiverId + "\"" +
                    "        ]" +
                    "    }," +
                    "    \"target_channel\": \"push\"," +
                    "    \"data\": {" +
                    "        \"conversationId\": " + conversationId + "," +
                    "        \"senderId\": " + senderId +
                    "    }," +
                    "    \"headings\": {" +
                    "        \"en\": \"" + title + "\"" +
                    "    }," +
                    "    \"contents\": {" +
                    "        \"en\": \"" + content + "\"" +
                    "    }" +
                    "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            String jsonResponse = mountResponseRequest(con, httpResponse);
            System.out.println("jsonResponse:\n" + jsonResponse);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException, IOException {
        String jsonResponse;
        if (  httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        return jsonResponse;
    }
}
