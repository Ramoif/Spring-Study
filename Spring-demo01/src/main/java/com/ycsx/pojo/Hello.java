package com.ycsx.pojo;

public class Hello {
    public Hello() {
    }

    private String str;

    @Override
    public String toString() {
        return "Hello{" +
                "str='" + str + '\'' +
                '}';
    }

    public String getStr() {
        return str;
    }

    /* beans中property需要调用这个set方法。所以setStr亮了。 */
    public void setStr(String str) {
        this.str = str;
    }
}
