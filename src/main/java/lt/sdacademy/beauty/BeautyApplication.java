package lt.sdacademy.beauty;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(basePackageClasses = { BeautyApplication.class, Jsr310JpaConverters.class })
@SpringBootApplication
public class BeautyApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BeautyApplication.class, args);
	}

}
