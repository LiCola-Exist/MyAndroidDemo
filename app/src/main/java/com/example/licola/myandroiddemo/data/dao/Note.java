package com.example.licola.myandroiddemo.data.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by LiCola on 2018/6/27.
 */
@Entity(
)
public class Note {
  @Id
  private Long id;

  @NotNull
  private String text;

  private String author;

  @Generated(hash = 270890949)
  public Note(Long id, @NotNull String text, String author) {
      this.id = id;
      this.text = text;
      this.author = author;
  }

  @Generated(hash = 1272611929)
  public Note() {
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

  public String getAuthor() {
      return this.author;
  }

  public void setAuthor(String author) {
      this.author = author;
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Note{");
    sb.append("id=").append(id);
    sb.append(", text='").append(text).append('\'');
    sb.append(", author='").append(author).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
