package com.example.weatherbroadcast;

import android.os.Handler;
import android.os.Message;

import android.widget.Toast;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class HttpsRequest implements Runnable {

  String city = "Amsterdam";
  static final String KEY = "474ce7bcb6d24f69b05150706240101";
  static final String APIREQUEST  = "https://api.weatherapi.com/v1/current.json";

  URL url;
  Handler handler;

  public HttpsRequest(android.os.Handler handler, String city) {
    if (!Objects.isNull(city)) {
      this.city = city;
    }
    this.handler = handler;
    try {
      url = new URL(APIREQUEST + "?" + "q=" + this.city + "&" + "key=" + KEY);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    try {
      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
      Scanner in = new Scanner(connection.getInputStream());
      StringBuilder response = new StringBuilder();
      while (in.hasNext()) {
        response.append(in.nextLine());
      }
      in.close();
      connection.disconnect();

      Message msg = Message.obtain();
      msg.obj = response.toString();

      handler.sendMessage(msg);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
