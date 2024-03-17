package cc.xmist.mistchat.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cc.xmist.mistchat.**.mapper")
public class MistChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MistChatServerApplication.class, args);
    }

}
