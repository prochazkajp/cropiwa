package com.steelkiwi.cropiwa;

import android.graphics.Bitmap;

public interface OnCroppedListener {
	void onBitmapCroppedListener(Bitmap bitmap);
	void onCropFailed(Throwable throwable);
}
