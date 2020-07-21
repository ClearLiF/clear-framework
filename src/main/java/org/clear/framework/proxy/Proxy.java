package org.clear.framework.proxy;

import org.clear.framework.proxy.ProxyChain;

/**
 * 无建议(默认)
 * @description
 * @author ClearLi
 * @date 2020/7/20 18:04
 */
public interface Proxy {
    /**
     * 执行链式代理
     * @description
     * @author ClearLi
     * @date 2020/7/21 9:13
     * @param proxyChain
     * @return java.lang.Object
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
