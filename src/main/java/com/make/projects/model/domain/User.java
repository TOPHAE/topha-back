package com.make.projects.model.domain;
import com.make.projects.model.domain.enumType.Provider;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends CommonDate {

    @Id
    @GeneratedValue
    private Long userId;

    private String nickname;

    @Column(nullable = false)
    private String email;

    private String specialty;

    private String imgUrl;

    private String roles;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();


    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

}
