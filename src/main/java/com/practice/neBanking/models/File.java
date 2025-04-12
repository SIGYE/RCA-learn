package com.practice.neBanking.models;

import jakarta.persistence.*;
import lombok.*;
import com.practice.neBanking.enums.EFileSizeType;
import com.practice.neBanking.enums.EFileStatus;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files", uniqueConstraints = {@UniqueConstraint(columnNames = "path")})
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Transient
    private String url;

    @Column(name = "size")
    private String size;

    @Column(name = "size_type")
    @Enumerated(EnumType.STRING)
    private EFileSizeType fileSizeType;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EFileStatus status;

    public String getUrl(){
        return "http://localhost:9904/api/v1/files/load-files" + "/" + this.getName();
    }
}
