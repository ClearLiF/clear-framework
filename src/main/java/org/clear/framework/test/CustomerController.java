package org.clear.framework.test;

import org.clear.framework.annotation.Autowired;
import org.clear.framework.annotation.Controller;
import org.clear.framework.annotation.RequestMap;
import org.clear.framework.bean.Data;
import org.clear.framework.bean.Param;
import org.clear.framework.bean.View;

import java.util.List;
import java.util.Map;

/**
 * @author clear
 * @since 2017-06-29.
 */
@Controller
public class CustomerController {


    /**
     * 进入 客户列表 界面
     */
    @RequestMap("get:/customer")
    public void index(Param param) {
        System.out.println("测试");

    }




}
