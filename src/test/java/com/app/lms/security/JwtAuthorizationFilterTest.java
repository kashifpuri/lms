package com.app.lms.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.app.lms.security.JwtAuthorizationFilter;
import com.app.lms.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtAuthorizationFilterTest {

    private JwtAuthorizationFilter filter;

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.clearContext();
        filter = new JwtAuthorizationFilter(new JwtUtil(), new ObjectMapper());
    }

    @AfterEach
    public void clearContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testDoFilter() throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);
        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrYXNoaWZAcHVyaS5jb20iLCJmaXJzdE5hbWUiOiIiLCJsYXN0TmFtZSI6IiIsImV4cCI6MTkxNzkzMjE1N30.sCm3qqpkd3HKBo_-jjMR_Yc46XcMfSGVmIMomocr0yA");

        filter.doFilterInternal(request,response,chain);

        verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));

        Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        Assertions.assertEquals("kashif@puri.com",SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    @Test
    public void testDoFilterFailure() throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);
        request.addHeader("Authorization", "Bearer eyJzdWIiOiJrYXNoaWZAcHVyaS5jb20iLCJmaXJzdE5hbWUiOiIiLCJsYXN0TmFtZSI6IiIsImV4cCI6MTkxNzkzMjE1N30.sCm3qqpkd3HKBo_-jjMR_Yc46XcMfSGVmIMomocr0yA");

        filter.doFilterInternal(request,response,chain);

        verify(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));

        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
