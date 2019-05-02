package com.hcl.cloud.cart.config;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(value = PowerMockRunner.class)
public class SwaggerConfigurationTest {

	@InjectMocks
	private SwaggerConfiguration swaggerConfigurationMock;
	
	/**
	 * This is a method for pre-processing tasks.
	 * @throws Exception
	 */
	@Before
    public void setUp() throws Exception {
		swaggerConfigurationMock = PowerMockito.spy(new SwaggerConfiguration());
        MockitoAnnotations.initMocks(this);
    }
	
	/**
	 * Test success for postsApi.
	 */
	@Test
    public final void testPostsApiSuccess() throws IOException {
		swaggerConfigurationMock.postsApi();
    }
}
