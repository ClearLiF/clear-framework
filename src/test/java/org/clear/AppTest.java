package org.clear;

import static org.junit.Assert.assertTrue;

import org.clear.framework.bean.JsonUtil;
import org.clear.framework.test.CustomerController;
import org.clear.framework.test.People;
import org.clear.framework.util.ReflectionUtil;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void TestName(){
        People people = new People();
        people.setName("liqd");
        people.setPassword("123456");
        String x = JsonUtil.toJson(people);
        System.out.println(x);
        System.out.println(JsonUtil.fromJson(x, People.class));
    }
}
