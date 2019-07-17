package org.red5.server.plugin.javascript;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;

public class JsUtil {

    public static <T> ProxyArray createProxyArray(List<T> list) {
        return new ProxyArray() {
            @Override
            public void set(long index, Value value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public long getSize() {
                return list.size();
            }

            @Override
            public Object get(long index) {
                return list.get((int) index);
            }
        };
    }

    public static <T> ProxyArray createProxyArray(Collection<T> coll) {
        return createProxyArray(new ArrayList<T>(coll));
    }

    public static <T> ProxyArray createProxyArray(Iterator<T> iterator) {
        List<T> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        return createProxyArray(list);
    }
}