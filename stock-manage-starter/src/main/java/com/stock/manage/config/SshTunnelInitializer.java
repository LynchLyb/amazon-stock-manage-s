package com.stock.manage.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "spring.datasource",name = "aliyun",havingValue = "true")
public class SshTunnelInitializer {

    @PostConstruct
    public void setupTunnel() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession("root", "121.41.112.124", 22);
            session.setPassword("958293Li.");
            session.setConfig("StrictHostKeyChecking", "no");
            session.setServerAliveInterval(60_000);  // 保持心跳
            session.connect();

            // 本地 13307 端口映射到远程 MySQL 3306
            session.setPortForwardingL(13307, "127.0.0.1", 3306);
            log.info("SSH Tunnel ready: localhost:13307 → remote:3306");

        } catch (Exception e) {
            throw new RuntimeException("Failed to establish SSH tunnel", e);
        }
    }

}