package org.javatraining.ws;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * Created by asudak on 5/29/15.
 */
public class AbstractService<T> {
    private Class<T> klass;

    public AbstractService(Class<T> genericClass) {
        klass = genericClass;
    }

    String serialize(T object) {
        return new JSONSerializer().serialize(object);
    }

    T deserialize(String json) {
        return new JSONDeserializer<T>().deserialize(json, klass);
    }
}
