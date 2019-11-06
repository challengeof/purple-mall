// package net.caidingke.chenxi.algorithm.consistenthash;
//
// import java.nio.charset.StandardCharsets;
// import java.security.MessageDigest;
// import java.security.NoSuchAlgorithmException;
// import java.util.List;
// import java.util.Map;
// import java.util.TreeMap;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.concurrent.ConcurrentMap;

// /**
//  * 一致性hash
//  *
//  * @author bowen
//  */
// public class ConsistentHashLoadBalance extends AbstractLoadBalance {
//
//     private final ConcurrentMap<String, ConsistentHashSelector<?>> selectors = new ConcurrentHashMap<String, ConsistentHashSelector<?>>();
//
//     @Override
//     protected <T> Server<T> doSelect(List<Server<T>> servers, Invocation invocation) {
//         int identityHashCode = System.identityHashCode(servers);
//         String key = invocation.getKey();
//         ConsistentHashSelector<T> selector = (ConsistentHashSelector<T>) selectors.get(key);
//         if (selector == null || selector.identityHashCode != identityHashCode) {
//             selectors.put(key, new ConsistentHashSelector<T>(servers, methodName, identityHashCode));
//             selector = (ConsistentHashSelector<T>) selectors.get(key);
//         }
//         return selector.select(invocation);
//     }
//
//
//     private static final class ConsistentHashSelector<T> {
//
//         private final TreeMap<Long, Server<T>> virtualServers;
//
//         private final int replicaNumber;
//
//         private final int identityHashCode;
//
//         private final int[] argumentIndex;
//
//         ConsistentHashSelector(List<Server<T>> invokers, int identityHashCode) {
//             this.virtualServers = new TreeMap<Long, Server<T>>();
//             this.identityHashCode = identityHashCode;
//             URL url = invokers.get(0).getUrl();
//             this.replicaNumber = url.getMethodParameter(methodName, HASH_NODES, 160);
//             String[] index = COMMA_SPLIT_PATTERN.split(url.getMethodParameter(methodName, HASH_ARGUMENTS, "0"));
//             argumentIndex = new int[index.length];
//             for (int i = 0; i < index.length; i++) {
//                 argumentIndex[i] = Integer.parseInt(index[i]);
//             }
//             for (Server<T> invoker : invokers) {
//                 String address = invoker.getUrl().getAddress();
//                 for (int i = 0; i < replicaNumber / 4; i++) {
//                     byte[] digest = md5(address + i);
//                     for (int h = 0; h < 4; h++) {
//                         long m = hash(digest, h);
//                         virtualServers.put(m, invoker);
//                     }
//                 }
//             }
//         }
//
//         public Server<T> select(Invocation invocation) {
//             String key = toKey(invocation.getArguments());
//             byte[] digest = md5(key);
//             return selectForKey(hash(digest, 0));
//         }
//
//         private String toKey(Object[] args) {
//             StringBuilder buf = new StringBuilder();
//             for (int i : argumentIndex) {
//                 if (i >= 0 && i < args.length) {
//                     buf.append(args[i]);
//                 }
//             }
//             return buf.toString();
//         }
//
//         private Server<T> selectForKey(long hash) {
//             Map.Entry<Long, Server<T>> entry = virtualServers.ceilingEntry(hash);
//             if (entry == null) {
//                 entry = virtualServers.firstEntry();
//             }
//             return entry.getValue();
//         }
//
//         private long hash(byte[] digest, int number) {
//             return (((long) (digest[3 + number * 4] & 0xFF) << 24)
//                     | ((long) (digest[2 + number * 4] & 0xFF) << 16)
//                     | ((long) (digest[1 + number * 4] & 0xFF) << 8)
//                     | (digest[number * 4] & 0xFF))
//                     & 0xFFFFFFFFL;
//         }
//
//         private byte[] md5(String value) {
//             MessageDigest md5;
//             try {
//                 md5 = MessageDigest.getInstance("MD5");
//             } catch (NoSuchAlgorithmException e) {
//                 throw new IllegalStateException(e.getMessage(), e);
//             }
//             md5.reset();
//             byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
//             md5.update(bytes);
//             return md5.digest();
//         }
//
//     }
// }
