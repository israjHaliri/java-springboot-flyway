package com.haliri.israj.javaspringbootflyway;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class User implements Serializable
{
    private static final long serialVersionUID = 5812550404897934356L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="b2c_account_id")
    private Long b2CAccountId;

    @Column(name="b2b_account_id")
    private Long b2BAccountId;
}
