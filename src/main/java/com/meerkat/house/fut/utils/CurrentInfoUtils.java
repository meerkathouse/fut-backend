package com.meerkat.house.fut.utils;

import lombok.Data;

@Data
public class CurrentInfoUtils {
    private static Integer uid;

    public static void setUid(Integer localUid) { uid = localUid; }
    public static Integer getUid() { return uid;}
}
