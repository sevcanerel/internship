package com.library.library;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class BookService {
	
	@Autowired 
	BookRepository repository;
	
	public List<BookEntity> getAllBooks(){
    	
    	return (List<BookEntity>) repository.findAll();
    	
    	
    }
	
	
	 public Optional<BookEntity> getBookById(int i){
	    	
	    	return repository.findById(i);
	    
	    	
	    }
	 
	    	
	    	
	    
	    	
	   
	 
	 
	 public void deleteAllBooks(){
			repository.deleteAll();
		}
	 
	 public void deleteBookById( int i){
			repository.deleteById(i);
		}

	 
	 public void addNewBook(BookEntity book){
		    
			repository.save(book);
		
		}
	 
	 
	 
	 
	 public void uptadeBook( BookEntity book,int i){
		 BookEntity bookFound=repository.findById(i).get();
		 bookFound.setAuthor(book.getAuthor());
		 bookFound.setBookImg(book.getBookImg());
		 bookFound.setCategory(book.getCategory());
		 bookFound.setDate(book.getDate());
		 bookFound.setPrice(book.getPrice());
		 bookFound.setPublisher(book.getPublisher());
		 bookFound.setTitle(book.getTitle());
		repository.save(bookFound);
		}

	
	

}
