package com.eatj.igorribeirolima.util.dadosinfomoney;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

class HTTPRequest {
  
  public static String get( String strUrl ){
    HttpClient httpClient = new DefaultHttpClient();
    HttpGet httpget = new HttpGet(strUrl);
    
    try {
      HttpResponse response = httpClient.execute(httpget);
      HttpEntity entity = response.getEntity();
      return getConteudo(entity);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  private static String getConteudo(HttpEntity entity) {
    InputStream instream = null;
    String conteudoUrl = null;

    // If the response does not enclose an entity, there is no need
    // to worry about connection release
    if (entity != null) {
      try {
        instream = entity.getContent();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(instream));
        // do something useful with the response
        String line = null;
        conteudoUrl = "";
        while ((line = reader.readLine()) != null)
          conteudoUrl += line + "\n";

      } catch (IOException ex) {
        // In case of an IOException the connection will be released
        // back to the connection manager automatically
        throw new RuntimeException(ex);
      } catch (RuntimeException ex) {
        // In case of an unexpected exception you may want to abort
        // the HTTP request in order to shut down the underlying
        // connection and release it back to the connection manager.
        throw new RuntimeException(ex);
      } finally {
        // Closing the input stream will trigger connection release
        try {
          instream.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          throw new RuntimeException(e);
        }
      }
    }

    return conteudoUrl;
  }
  
}
