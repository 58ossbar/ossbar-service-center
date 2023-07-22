package weixin.popular.api;

import com.alibaba.fastjson.JSONObject;

import java.nio.charset.Charset;

import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.popular.bean.CbBaseResult;
import weixin.popular.client.LocalHttpClient;

// Referenced classes of package weixin.popular.api:
//            BaseAPI, API

public class WxaLiveAPI extends BaseAPI {

    public WxaLiveAPI() {
    }

    public static CbBaseResult getliveinfo(String access_token, Integer start, Integer limit) {
        JSONObject json = new JSONObject();
        json.put("start", Integer.valueOf(start != null ? start.intValue() : 0));
        json.put("limit", Integer.valueOf(limit != null ? limit.intValue() : 10));
        org.apache.http.client.methods.HttpUriRequest httpUriRequest = RequestBuilder.post().setUri("https://api.weixin.qq.com/wxa/business/getliveinfo").addParameter("access_token", API.accessToken(access_token)).setEntity(new StringEntity(json.toJSONString(), Charset.forName("utf-8"))).build();
        return (CbBaseResult) LocalHttpClient.executeJsonResult(httpUriRequest, CbBaseResult.class);
    }

    private static Logger logger = LoggerFactory.getLogger(WxaLiveAPI.class);

}
