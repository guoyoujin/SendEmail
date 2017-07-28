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
    private String parentName;
    private int count;
    private boolean isChecked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileMode fileMode = (FileMode) o;

        if (count != fileMode.count) return false;
        if (isChecked != fileMode.isChecked) return false;
        if (path != null ? !path.equals(fileMode.path) : fileMode.path != null) return false;
        if (parent != null ? !parent.equals(fileMode.parent) : fileMode.parent != null)
            return false;
        if (name != null ? !name.equals(fileMode.name) : fileMode.name != null) return false;
        if (Type != null ? !Type.equals(fileMode.Type) : fileMode.Type != null) return false;
        return parentName != null ? parentName.equals(fileMode.parentName) : fileMode.parentName == null;

    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (Type != null ? Type.hashCode() : 0);
        result = 31 * result + (parentName != null ? parentName.hashCode() : 0);
        result = 31 * result + count;
        result = 31 * result + (isChecked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FileMode{" +
                "path='" + path + '\'' +
                ", parent='" + parent + '\'' +
                ", name='" + name + '\'' +
                ", Type='" + Type + '\'' +
                ", parentName='" + parentName + '\'' +
                ", count=" + count +
                ", isChecked=" + isChecked +
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
