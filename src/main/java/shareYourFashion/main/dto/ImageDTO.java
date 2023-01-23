package shareYourFashion.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shareYourFashion.main.constant.ImageType;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {

    @NotEmpty
    private String fileName;

    @NotEmpty
    private String fileOriginName;

    @NotEmpty
    private String storedFilePath;

    @NotEmpty
    private ImageType imageType;
}
