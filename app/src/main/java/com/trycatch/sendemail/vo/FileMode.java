package com.trycatch.sendemail.vo;

/**
 * 在此写用途
 *
 * @FileName: com.trycatch.sendemail.vo.FileMode.java
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2017-07-27 17:18
 * @version: V1.0 <描述当前版本功能>
 */

public class FileMode {
    private String path;
    private String parent;
    private String name;
    private String Type;

    @Override
    public String toString() {
        return "FileMode{" +
                "path='" + path + '\'' +
                ", parent='" + parent + '\'' +
                ", name='" + name + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
