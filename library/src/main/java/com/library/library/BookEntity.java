package com.library.library;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;



@Entity
@Table(name="books")
public class BookEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6144106813423602852L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NonNull
	private Integer id;
	
	@NonNull
	@Column(name="title")
	private String title;
	@Column(name="date")
	private Date date;

	@Column(name="price")
	private float price;
	@Lob
	@Column(name="book_img")
	private String bookImg;
	@Column(name="category")
	private String category;
	
	@ManyToOne
	@JoinColumn(name="author_id")
	private AuthorEntity author;
	
	
	@ManyToOne
	@JoinColumn(name="publisher_id")
	private PublisherEntity publisher;
	

	
	public String getBookImg() {
		return bookImg;
	}
	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	/*public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public int getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}*/
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public BookEntity(int id, String title, Date date, int authorId, int publisherId, float price, String bookImg,
			String category) {
		
		this.id = id;
		this.title = title;
		this.date = date;
		/*this.authorId = authorId;
		this.publisherId = publisherId;*/
		this.price = price;
		this.bookImg = bookImg;
		this.category = category;
	}
	public BookEntity() {

	}
	public AuthorEntity getAuthor() {
		return author;
	}
	public void setAuthor(AuthorEntity author) {
		this.author = author;
	}
	public PublisherEntity getPublisher() {
		return publisher;
	}
	public void setPublisher(PublisherEntity publisher) {
		this.publisher = publisher;
	}
	
   
}
