package org.clear.framework.proxy;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.clear.framework.annotation.Transaction;
import org.clear.framework.helper.DatabaseHelper;
/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : TransactionProxy
 * @packageName : org.clear.framework.proxy
 * @description : 事务代理
 * @date : 2020-07-21 10:28
 **/
@Slf4j
public class TransactionProxy implements Proxy {


    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                log.error("开启事务 begin transaction");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                log.error("提交事务 commit transaction");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                log.error("回滚事务 rollback transaction");
                throw e;
            } finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
