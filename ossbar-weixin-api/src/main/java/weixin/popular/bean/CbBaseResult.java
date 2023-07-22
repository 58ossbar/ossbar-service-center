package weixin.popular.bean;


import com.alibaba.fastjson.JSONObject;

public class CbBaseResult extends JSONObject {
    private static final long serialVersionUID = -3563449037179472726L;

    public boolean isSuccess() {
        Object object = get("errcode");
        return (object != null && Integer.parseInt(object.toString()) == 0);
    }
}
