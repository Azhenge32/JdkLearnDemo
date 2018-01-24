package com.azhen.java.lang;

/**
 * @author Azhen
 * @date 2018/01/24
 */
public class ByteTest {
    /**
     * cache 为了方便static Byte valueOf(byte b)运算
     * static class :在类加载器加载Byte类的时候就已初始化cache,只初始化一次
     * private 只能在Byte类内部访问ByteCache
     */
    public static Byte valueOf(byte b) {
        final int offset = 128;
        return ByteTest.ByteCache.cache[(int)b + offset];
    }

    private class newByte {
        {
            System.out.println("newByte");
        }
    }

    private static class ByteCache {
        private ByteCache(){}

        // 长度是256
        // 可读性高
        static final Byte cache[] = new Byte[-(-128) + 127 + 1];

        static {
            System.out.println("static init");
            System.out.println(cache.length);   // 256
            for (int i = 0; i < cache.length; i++) {
                cache[i] = new Byte((byte)(i - 128));
            }
            System.out.println(cache);// -128至127
        }
    }

    public static void main(String[] args) {
        new Byte("1"); // 不会打印static init
        ByteTest.valueOf((byte)1);  // 打印 "static init
    }
}
