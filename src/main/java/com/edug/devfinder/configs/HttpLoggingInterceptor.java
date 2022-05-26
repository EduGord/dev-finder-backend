package com.edug.devfinder.configs;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.ClassUtils;
import org.springframework.util.StopWatch;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE - 2)
public class HttpLoggingInterceptor extends AbstractHttpLoggingInterceptor implements ClientHttpRequestInterceptor {

    @NotNull
    private final Logger log = LoggerFactory.getLogger(ClassUtils.getUserClass(this.getClass()));

    @NotBlank
    private final String providerName;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
        var uuid = this.preExecute(log, providerName, request, bytes);
        var requestWatch = new StopWatch(uuid.toString());
        requestWatch.start();
        var response = execution.execute(request, bytes);
        requestWatch.stop();
        this.postExecute(log, providerName, uuid, request, response, Optional.of(requestWatch.getTotalTimeSeconds()));
        return response;
    }
}
