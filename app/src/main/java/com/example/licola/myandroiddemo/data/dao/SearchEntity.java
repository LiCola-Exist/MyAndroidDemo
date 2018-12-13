package com.example.licola.myandroiddemo.data.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author LiCola
 * @date 2018/12/6
 */
@Entity
public class SearchEntity {

  @Id
  private Long id;

  @NotNull
  private String text;

  @Generated(hash = 629878008)
  public SearchEntity(Long id, @NotNull String text) {
      this.id = id;
      this.text = text;
  }

  @Generated(hash = 1021466028)
  public SearchEntity() {
  }

  public Long getId() {
      return this.id;
  }

  public void setId(Long id) {
      this.id = id;
  }

  public String getText() {
      return this.text;
  }

  public void setText(String text) {
      this.text = text;
  }
}
