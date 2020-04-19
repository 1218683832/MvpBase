package com.mrrun.lib.mvpbase.network.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
	// 定义并配置gson
	private static final Gson gson = new GsonBuilder()// 建造者模式设置不同的配置
			.serializeNulls()// 序列化为null对象
			.setDateFormat("yyyy-MM-dd HH:mm:ss") // 设置日期的格式
			.disableHtmlEscaping()// 防止对网址乱码 忽略对特殊字符的转换
			.registerTypeAdapter(String.class, new StringConverter())// 对为null的字段进行转换
			.create();

	/**
	 * 对解析数据的形式进行转换
	 * 
	 * @param object
	 * @return 转化结果为json字符串
	 */
	public static String objectToJson(Object object) {
		String json = gson.toJson(object);
		return json;
	}

	/**
	 *
	 * @param object
	 * @param type
	 * @return 转化结果为json字符串
	 */
	public static String objectToJson(Object object, Type type) {
		String json = gson.toJson(object, type);
		return json;
	}

	/**
	 * 解析为一个具体的对象
	 *
	 * @param json
	 *            要解析的字符串
	 * @param classOfT
	 *            要解析的对象
	 * @param <T>
	 *            将json字符串解析成obj类型的对象
	 * @return
	 */
	public static <T> T jsonToObject(String json, Class<T> classOfT) {
		T object = gson.fromJson(json, classOfT);
		return object;
	}

	/**
	 * 解析为一个具体的对象
	 *
	 * @param json
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T> T jsonToObject(String json, Type type) {
		T object = gson.fromJson(json, type);
		return object;
	}

	/**
	 * 对象转化为HashMap
	 *
	 * @param object
	 * @return
	 */
	public static <T> HashMap<String, T> objectToHashMap(Object object, Class<T> clz) {
		HashMap<String, T> map = new HashMap<String, T>();
		String json = objectToJson(object);
		map = jsonToHashMap(json, clz);
		return map;
	}

	public static <T> List<HashMap<String, T>> jsonToHashMapList(String json, Class<T> clz) {
		List<HashMap<String, T>> list = new ArrayList<HashMap<String, T>>();
		list.add(jsonToHashMap(json, clz));
		return list;
	}

	/**
	 * Json转化为Map
	 *
	 * @param jsonStr
	 * @param clz
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, T> jsonToMap(String jsonStr, Class<T> clz) {
		Map<String, T> map = gson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {}.getType());
		return map;
	}

	public static <T> HashMap<String, T> jsonToHashMap(String jsonStr, Class<T> clz) {
		HashMap<String, T> map = gson.fromJson(jsonStr, new TypeToken<HashMap<String, T>>() {}.getType());
		return map;
	}

	/**
	 * Json转化为List
	 */
	public static <T> List<T> jsonToList(String jsonStr, Class<T> clz) {
		List<T> list = gson.fromJson(jsonStr, new TypeToken<List<T>>() {}.getType());
		return list;
	}

	/**
	 * 实现了序列化接口对为null的字段进行转换
	 */
	private static class StringConverter implements JsonSerializer<String>,
			JsonDeserializer<String> {
		// 字符串为null转换成"",否则为字符串类型
		@Override
		public String deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			return json.getAsJsonPrimitive().getAsString();
		}

		@Override
		public JsonElement serialize(String src, Type typeOfSrc,
				JsonSerializationContext context) {
			return src == null || src.equals("null") ? new JsonPrimitive("")
					: new JsonPrimitive(src);
		}
	}
}
