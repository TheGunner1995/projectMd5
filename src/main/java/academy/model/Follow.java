package academy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;
    private LocalDate date;
    private boolean status;
    @ManyToOne()
    @JoinColumn(name = "followList")
    @JsonIgnoreProperties({"roles"})
    private Users follow;

    @ManyToOne()
    @JoinColumn(name = "userId")
//    @JsonIgnore
    private Users users;
}
