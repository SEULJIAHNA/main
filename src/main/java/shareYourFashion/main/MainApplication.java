package shareYourFashion.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing // 엔터티 데이터가 업데이트 , 생성됨에 따라 createdAt , updatedAt를 자동 업데이트 하기 위해
@EntityScan("shareYourFashion.main.domain")
@EnableJpaRepositories("shareYourFashion.main.repository")
@ComponentScan({"shareYourFashion.main"})
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
