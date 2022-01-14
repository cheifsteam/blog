package com.qrx.blog.util;

import java.net.URI;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
public class MyBlogUtils {

    public static URI getHost(URI uri) {
        URI effectiveURI = null;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (Throwable var4) {
            effectiveURI = null;
        }
        return effectiveURI;
    }
}