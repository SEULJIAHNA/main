package shareYourFashion.main.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // BaseTimeEntity를 상속한 엔티티들은 아래 필드들을 컬럼으로 인식한다.
@EntityListeners(AuditingEntityListener.class) // createAt , updateAt에 대한 자동 값 매핑 (auditing) 기능 추가
@Data
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime LastModifiedDate;
}