import com.sun.demo.Application;
import com.sun.demo.mybatis.entity.Org;
import com.sun.demo.mybatis.mapper.OrgMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @auther zhsun5@iflytek.com
 * @date 2018/1/8
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class App {
    @Autowired
    private OrgMapper orgMapper;

    @Test
    public void queryOrg(){
        List<Org> list = orgMapper.queryOrg();
        for (Org org : list) {
            System.out.println(org.getName());
            for (Org o : org.getChildren()) {
                System.out.println("\t" + o.getName());
            }
        }
    }
}
