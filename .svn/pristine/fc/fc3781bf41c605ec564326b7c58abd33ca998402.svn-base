package com.cheddd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/29 0029.
 */

public class SerializableUtil {
    public static <T extends Serializable> void write(T t, String outPath) {
        ObjectOutputStream oos = null;
        try {
            File file = new File(outPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(t);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Serializable read(String path) throws Exception {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(path));
            Object object = ois.readObject();

            if (object != null) {
                return (Serializable) object;
            }
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
        return null;
    }
}
