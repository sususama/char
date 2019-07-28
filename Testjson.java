package text.edu.xatu;

import net.sf.json.JSONObject;
import org.junit.Test;

public class Testjson {
    @Test
    public void Test1(){
        Studnet s=new Studnet("zhangsan",12,1001);
        JSONObject js= JSONObject.fromObject(s);
        System.out.println(js.toString());
    }
    @Test
    public void Test2(){
        String jsstr="{\"age\":12,\"namber\":1001,\"name\":\"zhangsan\"}";
        JSONObject object=JSONObject.fromObject(jsstr);
        Studnet o= (Studnet) JSONObject.toBean(object,Studnet.class);
        System.out.println(o);

    }
}
