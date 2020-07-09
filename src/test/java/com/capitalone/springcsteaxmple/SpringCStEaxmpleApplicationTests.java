package com.capitalone.springcsteaxmple;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringCStEaxmpleApplicationTests {
    public static final int FIRST_MSG_BINDING_NAME = 0;
    public static final int SECOND_MSG_BINDING_NAME = 1;

    @Test
    public void testMultipleFunctions() {
        try (ConfigurableApplicationContext context
                     = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(SpringCStEaxmpleApplication.class)
        ).run("--spring.cloud.function.definition=upperCase;reverse")) {

            InputDestination inputDestination = context.getBean(InputDestination.class);
            OutputDestination outputDestination = context.getBean(OutputDestination.class);

            Message<byte[]> inputMessage = MessageBuilder.withPayload("Hello".getBytes()).build();
            inputDestination.send(inputMessage, FIRST_MSG_BINDING_NAME);
            inputDestination.send(inputMessage, SECOND_MSG_BINDING_NAME);

            Message<byte[]> outputMessage = outputDestination.receive(0, FIRST_MSG_BINDING_NAME);
            assertThat(outputMessage.getPayload()).isEqualTo("HELLO".getBytes());

            outputMessage = outputDestination.receive(0, SECOND_MSG_BINDING_NAME);
            assertThat(outputMessage.getPayload()).isEqualTo("olleH".getBytes());
        }
    }

    @Test
    public void sampleTest() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(
                        SpringCStEaxmpleApplication.class))
                .run("--spring.cloud.function.definition=echo")) {
            InputDestination source = context.getBean(InputDestination.class);
            OutputDestination target = context.getBean(OutputDestination.class);

            GenericMessage<byte[]> inputMessage = new GenericMessage<>("hello".getBytes());

            source.send(inputMessage);
            assertThat(target.receive().getPayload()).isEqualTo("hello".getBytes());
        }
    }
}
