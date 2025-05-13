package com.unitTestGenerator.util;

import com.unitTestGenerator.ioc.anotations.Component;

import java.io.*;
import java.util.Base64;

@Component
public class ObjectTransformer {


    public  <T> String objectToString(T objeto) {
        if (objeto == null) {
            return null;
        }
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(objeto);
            byte[] bytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public  <T> T stringToObject(String objetoString) {
        if (objetoString == null || objetoString.isEmpty()) {
            return null;
        }
        byte[] bytes = Base64.getDecoder().decode(objetoString);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            @SuppressWarnings("unchecked")
            T objeto = (T) ois.readObject();
            return objeto;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}



