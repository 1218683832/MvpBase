package com.mrrun.lib.mvpbase.network.retrofit;

import com.google.gson.Gson;
import com.mrrun.lib.mvpbase.network.result.BaseResult;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

public class ObjectConverter implements Converter {

	private final Gson gson;
	private boolean mIsNeedUngzip;

	public ObjectConverter(Gson gson) {
		this.gson = gson;
		mIsNeedUngzip = true;
	}

	public ObjectConverter(Gson gson, boolean isNeedUngzip) {
		this.gson = gson;
		mIsNeedUngzip = isNeedUngzip;
	}

	@Override
	public BaseResult fromBody(TypedInput body, Type type) throws ConversionException {
		InputStream is = null;
		try {
			is = body.in();
			byte[] bytes = IOUtils.toByteArray(is);
			if (mIsNeedUngzip) {
				bytes = IOUtils.ungzip(bytes);
			}
			String string = new String(bytes, StandardCharsets.UTF_8);
			BaseResult baseResult = gson.fromJson(string, type);
			baseResult.setJson(string);
			return baseResult;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public TypedOutput toBody(Object object) {
		return null;
	}
}