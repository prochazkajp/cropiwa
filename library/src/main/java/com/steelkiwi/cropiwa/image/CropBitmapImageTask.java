package com.steelkiwi.cropiwa.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import com.steelkiwi.cropiwa.OnCroppedListener;
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig;
import com.steelkiwi.cropiwa.shape.CropIwaShapeMask;
import com.steelkiwi.cropiwa.util.CropIwaUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

class CropBitmapImageTask extends AsyncTask<Void, Void, Throwable> {

	private CropArea cropArea;
	private CropIwaShapeMask mask;
	private Bitmap bitmap;
	private WeakReference<OnCroppedListener> listener;

	public CropBitmapImageTask(CropArea cropArea, CropIwaShapeMask mask, Bitmap bitmap,
							   OnCroppedListener listener) {
		this.cropArea = cropArea;
		this.mask = mask;
		this.bitmap = bitmap;
		this.listener = new WeakReference<>(listener);
	}

	@Override
	protected Throwable doInBackground(Void... params) {
		try {

			Bitmap cropped = cropArea.applyCropTo(bitmap);

			bitmap = mask.applyMaskTo(cropped);

//			Uri dst = saveConfig.getDstUri();
//			OutputStream os = context.getContentResolver().openOutputStream(dst);
//			cropped.compress(saveConfig.getCompressFormat(), saveConfig.getQuality(), os);
//			CropIwaUtils.closeSilently(os);

			cropped.recycle();
		} catch (Exception e) {
			return e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Throwable throwable) {
		if(listener.get() != null) {
			if (throwable == null) {
				listener.get().onBitmapCroppedListener(bitmap);
			} else {
				listener.get().onCropFailed(throwable);
			}
		}
	}
}