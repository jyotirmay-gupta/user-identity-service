package com.jyotirmay.useridentity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;

public class VersionHeaderFilterTest {

    private static final String ACCEPT_VERSION = "Accept-Version";
    private static final String ACCEPT_VERSION_V1 = "v1";

    private final VersionHeaderFilter filter = new VersionHeaderFilter();

    @Test
    @DisplayName("Should add Accept-Version header with v1 if missing")
    void givenHttpRequestWithoutAcceptVersion_whenFilter_thenAddAcceptVersionV1() throws IOException, ServletException {
        HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
        ServletResponse servletResponse = Mockito.mock(ServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        // Simulate getHeader(ACCEPT_VERSION) returns null => header missing
        Mockito.when(httpRequest.getHeader(ACCEPT_VERSION)).thenReturn(null);

        // Use an ArgumentCaptor to capture the wrapped request passed to filterChain
        ArgumentCaptor<ServletRequest> requestCaptor = ArgumentCaptor.forClass(ServletRequest.class);

        filter.doFilter(httpRequest, servletResponse, filterChain);

        Mockito.verify(filterChain).doFilter(requestCaptor.capture(), Mockito.eq(servletResponse));
        ServletRequest wrappedRequest = requestCaptor.getValue();

        Assertions.assertTrue(wrappedRequest instanceof HttpServletRequestWrapper);

        HttpServletRequestWrapper wrapper = (HttpServletRequestWrapper) wrappedRequest;
        Assertions.assertEquals(ACCEPT_VERSION_V1, wrapper.getHeader(ACCEPT_VERSION));
    }

    @Test
    @DisplayName("Should not overwrite Accept-Version header if present")
    void givenHttpRequestWithAcceptVersion_whenFilter_thenKeepOriginalHeader() throws IOException, ServletException {
        HttpServletRequest httpRequest = Mockito.mock(HttpServletRequest.class);
        ServletResponse servletResponse = Mockito.mock(ServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        Mockito.when(httpRequest.getHeader(ACCEPT_VERSION)).thenReturn("v2");

        ArgumentCaptor<ServletRequest> requestCaptor = ArgumentCaptor.forClass(ServletRequest.class);

        filter.doFilter(httpRequest, servletResponse, filterChain);

        Mockito.verify(filterChain).doFilter(requestCaptor.capture(), Mockito.eq(servletResponse));
        ServletRequest wrappedRequest = requestCaptor.getValue();

        Assertions.assertTrue(wrappedRequest instanceof HttpServletRequestWrapper);

        HttpServletRequestWrapper wrapper = (HttpServletRequestWrapper) wrappedRequest;
        // It should return the original header value ("v2")
        Assertions.assertEquals("v2", wrapper.getHeader(ACCEPT_VERSION));
    }

    @Test
    @DisplayName("Should pass through non-HTTP requests unmodified")
    void givenNonHttpRequest_whenFilter_thenPassThrough() throws IOException, ServletException {
        ServletRequest request = Mockito.mock(ServletRequest.class);
        ServletResponse response = Mockito.mock(ServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        filter.doFilter(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
    }
}
