package rol.Cliente.dao.utils;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;

import java.io.IOException;
import java.net.CookieHandler;
import java.util.*;

public class JavaNetCookieJar implements CookieJar {
    private final CookieHandler cookieHandler;


    public JavaNetCookieJar(CookieHandler cookieHandler) {
        this.cookieHandler = cookieHandler;
    }

    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (this.cookieHandler != null) {
            List<String> cookieStrings = new ArrayList();
            Iterator var4 = cookies.iterator();

            while (var4.hasNext()) {
                Cookie cookie = (Cookie) var4.next();
                cookieStrings.add(cookie.toString());
            }

            Map multimap = Collections.singletonMap(ConstantesParametros.SET_COOKIE, cookieStrings);

            try {
                this.cookieHandler.put(url.uri(), multimap);
            } catch (IOException var6) {
                Platform.get().log(5, ConstantesParametros.SAVING_COOKIES_FAILED_FOR + url.resolve(ConstantesParametros.BARRA_PUNTOS), var6);
            }
        }

    }

    public List<Cookie> loadForRequest(HttpUrl url) {
        Map headers = Collections.emptyMap();

        Map cookieHeaders;
        try {
            cookieHeaders = this.cookieHandler.get(url.uri(), headers);
        } catch (IOException var10) {
            Platform.get().log(5, ConstantesParametros.LOADING_COOKIES_FAILED_FOR + url.resolve(ConstantesParametros.BARRA_PUNTOS), var10);
            return Collections.emptyList();
        }

        List<Cookie> cookies = null;
        Iterator var5 = cookieHeaders.entrySet().iterator();

        while (true) {
            Map.Entry entry;
            String key;
            do {
                do {
                    if (!var5.hasNext()) {
                        return cookies != null ? Collections.unmodifiableList(cookies) : Collections.emptyList();
                    }

                    entry = (Map.Entry) var5.next();
                    key = (String) entry.getKey();
                } while (!ConstantesParametros.COOKIE.equalsIgnoreCase(key) && !ConstantesParametros.COOKIE_2.equalsIgnoreCase(key));
            } while (((List) entry.getValue()).isEmpty());

            String header;
            for (Iterator var8 = ((List) entry.getValue()).iterator(); var8.hasNext(); cookies.addAll(this.decodeHeaderAsJavaNetCookies(url, header))) {
                header = (String) var8.next();
                if (cookies == null) {
                    cookies = new ArrayList();
                }
            }
        }
    }

    private List<Cookie> decodeHeaderAsJavaNetCookies(HttpUrl url, String header) {
        List<Cookie> result = new ArrayList();
        int pos = 0;

        int pairEnd;
        for (int limit = header.length(); pos < limit; pos = pairEnd + 1) {
            pairEnd = Util.delimiterOffset(header, pos, limit, ";,");
            int equalsSign = Util.delimiterOffset(header, pos, pairEnd, '=');
            String name = Util.trimSubstring(header, pos, equalsSign);
            if (!name.startsWith("$")) {
                String value = equalsSign < pairEnd ? Util.trimSubstring(header, equalsSign + 1, pairEnd) : "";
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }

                result.add((new Cookie.Builder()).name(name).value(value).domain(url.host()).build());
            }
        }

        return result;
    }
}
