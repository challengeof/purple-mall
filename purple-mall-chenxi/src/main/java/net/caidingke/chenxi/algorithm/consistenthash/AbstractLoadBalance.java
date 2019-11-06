package net.caidingke.chenxi.algorithm.consistenthash;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author bowen
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    /**
     * select one server in list.
     *
     * @param servers    servers
     * @param invocation invocation
     * @return selected server.
     */
    protected abstract <T> Server<T> doSelect(List<Server<T>> servers, Invocation invocation);

    @Override
    public <T> Server<T> select(List<Server<T>> servers, Invocation invocation) {
        if (CollectionUtils.isEmpty(servers)) {
            return null;
        }
        if (servers.size() == 1) {
            return servers.get(0);
        }

        return doSelect(servers, invocation);
    }
}
