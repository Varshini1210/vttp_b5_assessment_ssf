package vttp.batch5.ssf.noticeboard.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.json.JsonValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {
    
    @NotEmpty(message="The title is a mandatory field.")
    @Size(min=3, max=128, message="The notice title's length must be between 3 and 128 characters.")
    private String title;

    @NotEmpty(message="The poster email is a mandatory field.")
    @Email(message="Please enter a valid email address.")
    private String poster;

    @NotNull(message="The post date is a mandatory field.")
    @Future(message="The post date must be a future date.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate;

    @NotEmpty(message="This is a mandatory field. Please include at least one category.")
    String[] categories;

    @NotEmpty(message="The text field is a mandatory field.")
    private String text;
    
    public Notice(String title, String poster, Date postDate, String[] categories, String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }

    public Notice() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
}
