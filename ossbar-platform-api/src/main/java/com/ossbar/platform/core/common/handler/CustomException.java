package com.ossbar.platform.core.common.handler;

import org.springframework.web.client.RestClientException;

/**
 * @author huj
 * @create 2022-08-15 15:47
 * @email hujun@creatorblue.com
 * @company 创蓝科技 www.creatorblue.com
 */
public class CustomException extends RestClientException {

    private RestClientException restClientException;

    private String body;

    public RestClientException getRestClientException() {
        return restClientException;
    }

    public void setRestClientException(RestClientException restClientException) {
        this.restClientException = restClientException;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public CustomException(String msg, RestClientException restClientException, String body) {
        super(msg);
        this.restClientException = restClientException;
        this.body = body;
    }
}
