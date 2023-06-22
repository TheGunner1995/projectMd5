package academy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String text;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private Users users;
    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;
}
