package academy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String fullName;
    @Column(unique = true)
    private String userName;
    private int age;
    @JsonIgnore
    private String password;
    @Column(unique = true)
    @Email
    private String email;
    private String phone;

    @OneToMany(mappedBy = "follow",targetEntity = Follow.class,fetch = FetchType.LAZY)
    @JsonIgnore
    List<Follow> followList;

    @JsonIgnore
    private boolean status = true;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Roles> roles = new HashSet<>();
}
