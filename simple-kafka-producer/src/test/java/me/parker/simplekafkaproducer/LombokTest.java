package me.parker.simplekafkaproducer;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class LombokTest {

    @Test
    void name() {
        Dto dto = new Dto();
        dto.setA("aaa");

        log.info("{}", dto.getA());
    }

    @Setter
    @Getter
    public static class Dto {
        private String a;
    }
}
