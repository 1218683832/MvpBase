package com.mrrun.lib.mvpbase.network.result;

/**
 * 
 * <br>类描述:基础的返回结果
 * <br>功能详细描述:
 * 
 * @author  xiaodong
 * @date  [2015-8-26]
 */
public class BaseResult {

    private String mJson;//保存服务器返回的json数据

    public void setJson(String json) {
        mJson = json;
    }

    public String getJson() {
        return mJson;
    }

}
