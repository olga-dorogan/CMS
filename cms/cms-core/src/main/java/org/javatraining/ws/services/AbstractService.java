package org.javatraining.ws.services;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * Created by asudak on 5/29/15.
 */
public abstract class AbstractService<T> {
    private Class<T> klass;

    public AbstractService(Class<T> genericClass) {
        klass = genericClass;
    }

    String serialize(Object object) {
        return new JSONSerializer().exclude("*.class").deepSerialize(object);
    }

    T deserialize(String json) {
        return new JSONDeserializer<T>().deserialize(json, klass);
    }
}
