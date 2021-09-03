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
public class Users extends CommonDate {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    private String specialty;

    private String imgUrl;

    private String roles;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;


    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

}
