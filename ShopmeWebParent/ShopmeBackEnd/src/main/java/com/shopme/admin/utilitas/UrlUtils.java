package com.shopme.admin.utilitas;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class UrlUtils {
	@Value("${server.servlet.context-path}")
    private String contextPath;

    private final HttpServletRequest request;
    private final Environment environment;

    public UrlUtils(HttpServletRequest request, Environment environment) {
        this.request = request;
        this.environment = environment;
    }

    public String getBaseUrl() {
        String scheme = request.getScheme();             // http
        String serverName = request.getServerName();     // localhost
        int serverPort = request.getServerPort();        // 8080

        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(scheme).append("://").append(serverName);

        // Tambahkan port jika tidak standar (80 untuk HTTP, 443 untuk HTTPS)
        if (("http".equals(scheme) && serverPort != 80) || ("https".equals(scheme) && serverPort != 443)) {
            baseUrl.append(":").append(serverPort);
        }

        // Tambahkan konteks path jika ada
        if (contextPath != null && !contextPath.isEmpty() && !"/".equals(contextPath)) {
            baseUrl.append(contextPath);
        }

        return baseUrl.toString();
    }
}
