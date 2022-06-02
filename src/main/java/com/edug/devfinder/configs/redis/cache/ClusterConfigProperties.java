package com.edug.devfinder.configs.redis.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@ConfigurationProperties(prefix = "spring.redis.cluster")
public class ClusterConfigProperties {

    /*
     * spring.redis.cluster.nodes[0] = 127.0.0.1:7000
     * spring.redis.cluster.nodes[1] = 127.0.0.1:7001
     * spring.redis.cluster.nodes[1] = 127.0.0.1:7002
     * ...
     */
    @NotEmpty
    List<String> nodes;

    /**
     * Get initial collection of known cluster nodes in format {@code host:port}.
     *
     * @return a list of host:port representing nodes available
     */
    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}