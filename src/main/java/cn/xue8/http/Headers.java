package cn.xue8.http;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: http
 * @description:
 * @author: Xue 8
 * @create: 2019-04-07 11:32
 **/
public final class Headers {
    private final List<List<String>> nameAndValues;

    Headers(Builder builder) {
        this.nameAndValues = builder.nameAndValues;
    }

    public String header(String name) {
        if (name == null)
            throw new NullPointerException("name == null");
        else {
            for (int i = 0; i < nameAndValues.size(); i++) {
                if (nameAndValues.get(i).get(0).equals(name))
                    return nameAndValues.get(i).get(1);
            }
        }
        return null;
    }

    public void addHeader(String name, String value) {
        List list = new ArrayList(2);
        list.add(name);
        list.add(value);
        for (int i = 0; i < nameAndValues.size(); i++) {
            if (nameAndValues.get(i).get(0).equals(name))
                nameAndValues.set(i, list);
            else
                nameAndValues.add(list);
        }
    }

    public List<List<String>> getNameAndValues() {
        return nameAndValues;
    }

    public static class Builder {
        private final List<List<String>> nameAndValues;

        Builder() {
            nameAndValues = new ArrayList<>(20);
        }

        public Builder add(String name, String value) {
            if (name == null || value == null)
                throw new NullPointerException("name or value == null");
            else {
                List<String> list = new ArrayList<>(2);
                if (name.equals("GET") || name.equals("POST")
                    || name.equals("PUT") || name.equals("DELETE")) {
                    list.add(name + " ");
                    list.add(value);
                } else {
                    list.add(name + ": ");
                    list.add(value);
                }
                nameAndValues.add(list);
                return this;
            }
        }

        public Builder remove(String name) {
            if (name == null)
                throw new NullPointerException("name == null");
            else {
                for (int i = 0; i < nameAndValues.size(); i++) {
                    if (nameAndValues.get(i).get(0).equals(name)) {
                        nameAndValues.remove(i);
                        return this;
                    }
                }
                return this;
            }
        }

        public Headers build() {
            return new Headers(this);
        }

    }
}
