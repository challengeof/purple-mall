package net.caidingke.chenxi.algorithm.consistenthash;

import java.util.List;

/**
 * @author bowen
 */
public interface LoadBalance {

    /**
     * select one server in list.
     *
     * @param servers    servers
     * @param invocation invocation
     * @return selected server.
     */
    <T> Server<T> select(List<Server<T>> servers, Invocation invocation);
}
