package shareYourFashion.main.domain;

import shareYourFashion.main.domain.valueTypeClass.Clothing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LookBook extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lookBook_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @Embedded
    @Column(nullable = true)
    private Clothing clothing;

    @Lob
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "lookBook" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<LookImage> lookImages = new ArrayList<>();

    @OneToMany( mappedBy = "lookBook"  , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @Column(nullable = false)
    @OneToMany(mappedBy = "lookBook",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<HashTag> hashTags = new ArrayList<>();

    @OneToMany(mappedBy = "lookBook" ,cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Comment> Comments = new ArrayList<>();



    // = 연관관계 메소드 =//
    public void setUser(User user){
        this.writer = user;
        user.getLookBooks().add(this);
    }

}
