package text.edu.xatu;

import edu.xatu.util.JSONUtil;
import org.junit.Test;

public class TwstJSONUtil {
    @Test
    public void Test1(){
        Studnet s=new Studnet("zhangsan",12,1001);
        System.out.println(JSONUtil.boj2json(s));
        String jsstr="{\"age\":12,\"namber\":1001,\"name\":\"zhangsan\"}";
        Studnet js=JSONUtil.json2obj(jsstr,Studnet.class);
        System.out.println(js);
    }
}
