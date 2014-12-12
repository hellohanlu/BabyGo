package com.hl.ui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TakePhotoActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
}

class Preview extends SurfaceView implements SurfaceHolder.Callback{
	
	SurfaceHolder mHolder;
	Camera mCamera;
	Bitmap cameraBitmap;
	
	public Preview(Context context) {
		super(context);
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// Æô¶¯Camera
		mCamera = Camera.open();
		try {
			mCamera.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO: handle exception
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPictureFormat(PixelFormat.JPEG);
		parameters.setPreviewSize(200, 200);
		mCamera.setParameters(parameters);
		mCamera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mCamera.stopPreview();
		mCamera = null;
	}
	
	private PictureCallback jpegCallback = new PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			cameraBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			File myCaptureFile = new File("/sdcard/camera1.jpg");
			try {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
				cameraBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
				bos.flush();
				bos.close();
				
				Canvas canvas = mHolder.lockCanvas();
				canvas.drawBitmap(cameraBitmap, 0, 0,null);
				mHolder.unlockCanvasAndPost(canvas);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};
	
}