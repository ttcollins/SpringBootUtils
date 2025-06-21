package org.savea.unit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.savea.filters.ClientIpFilter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class ClientIpFilterTest {

    @Test
    void addsRemoteAddressToRequest() throws ServletException, IOException {
        ClientIpFilter filter = new ClientIpFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = Mockito.mock(FilterChain.class);

        request.setRemoteAddr("127.0.0.1");

        filter.doFilter(request, response, chain);

        assertThat(request.getAttribute(ClientIpFilter.CLIENT_IP))
                .isEqualTo("127.0.0.1");
        verify(chain).doFilter(request, response);
    }
}
