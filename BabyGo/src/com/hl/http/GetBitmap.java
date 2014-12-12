package com.hl.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
/**
 * ¶ÁÈ¡ÍøÂçÍ¼Æ¬
 * @author ysh
 *
 */
public class GetBitmap {
	public static Bitmap getBitmap(String url) {
		Bitmap bt = null;
		if (url != null && !url.equals("")) {
			InputStream is = null;
			try {
				URL url1 = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) url1
						.openConnection();

				conn.setDoInput(true);
				conn.connect();
				is = conn.getInputStream();
				bt = BitmapFactory.decodeStream(is);
				is.close();
				conn.disconnect();

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return bt;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return bt;
			}

		}

		return bt;

	}

}