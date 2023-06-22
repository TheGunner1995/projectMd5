package academy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @OneToMany(mappedBy = "post", targetEntity = Image.class)
    @JsonIgnoreProperties("post")
    private List<Image> image;

    @OneToMany(mappedBy = "post", targetEntity = Comment.class)
    @JsonIgnoreProperties("post")
    private List<Comment> comment;
    private String content;
    private LocalDate date;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private Users users;
    private int like;
    @JsonIgnore
    private boolean status = true;

}
