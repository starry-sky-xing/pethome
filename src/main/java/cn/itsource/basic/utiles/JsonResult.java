package cn.itsource.basic.utiles;

import lombok.Data;

@Data
public class JsonResult

{
    //成功 默认true
    private Boolean success=true;
    private String msg;//错误信息
    private Object resultObj;//文件信息

    public JsonResult(String msg) {
        this.success = false;
        this.msg = msg;
    }

    public JsonResult() {

    }

    public static JsonResult me(String msg){

        return new JsonResult(msg);
    }

    public static JsonResult ResultObj(Object o){
        JsonResult jsonResult = new JsonResult();
        jsonResult.setResultObj(o);
        return jsonResult;
    }
}
