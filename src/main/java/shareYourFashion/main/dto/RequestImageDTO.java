package shareYourFashion.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import shareYourFashion.main.constant.ImageType;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestImageDTO { //  이미지 업로드 시 클라이언트 측 image 데이터를 컨트롤러에 전달하는 역할

    @NotEmpty
    private String imageType;

    @NotEmpty
    private String fileName;

    @NotEmpty
    private String formData;
}
