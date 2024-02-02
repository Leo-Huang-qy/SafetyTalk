package com.leo.safetytalk.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Data
public class ApiResponse {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "HTTP 状态码")
    private Integer status;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    public static ApiResponse ok(){
        return new ApiResponse()
                .success(true)
                .status(HttpStatus.OK.value())
                .message("成功");
    }

    public static ApiResponse error(){
        return new ApiResponse()
                .success(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("失败");
    }

    public ApiResponse success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public ApiResponse message(String message){
        this.setMessage(message);
        return this;
    }

    public ApiResponse status(Integer code){
        this.setStatus(code);
        return this;
    }

    public ApiResponse data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public ApiResponse data(Map<String, Object> map){
        this.data.putAll(map);
        return this;
    }

}
