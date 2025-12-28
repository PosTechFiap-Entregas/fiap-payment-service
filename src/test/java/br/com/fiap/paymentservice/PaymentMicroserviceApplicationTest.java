package br.com.fiap.paymentservice;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class PaymentMicroserviceApplicationTest {

    @Test
    void applicationHasSpringBootApplicationAnnotation() {
        assertTrue(PaymentMicroserviceApplication.class.isAnnotationPresent(SpringBootApplication.class));
    }

    @Test
    void main_invokesSpringApplicationRun() {
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {
            ConfigurableApplicationContext ctx = mock(ConfigurableApplicationContext.class);
            mocked.when(() -> SpringApplication.run(PaymentMicroserviceApplication.class, new String[0])).thenReturn(ctx);

            // call main with empty args to match the mocked signature
            PaymentMicroserviceApplication.main(new String[0]);

            // if no exception thrown, assume success; we can also verify invocation via the mock's recorded invocations
            mocked.verify(() -> SpringApplication.run(PaymentMicroserviceApplication.class, new String[0]));
        }
    }
}
