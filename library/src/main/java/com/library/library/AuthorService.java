package com.library.library;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorService {
	
	@Autowired 
	AuthorRepository repository;
	@Autowired 
	BookRepository bookRepository;
	
	
	
	 public Optional<AuthorEntity> getAuthorById(int i){
	    	
	    	return repository.findById(i);
	    
	    	
	    }
	 
	 
	
	 public void deleteAuthor(AuthorEntity author){
			
			int i=author.getId();
			repository.deleteById(i);
			
		}
	
	 public void deleteAuthorById( int i){
			
			repository.deleteById(i);
			
			
		}
	 
	 public void addNewAuthor(AuthorEntity author){
			
			repository.save(author);
			
		}
	 
	 
	 
	 
	 public void uptadeAuthor( AuthorEntity author,int i){
		 AuthorEntity authorFound=repository.findById(i).get();
		 authorFound.setFirstName(author.getFirstName());
		 authorFound.setLastName(author.getLastName());
		 
		repository.save(authorFound);
			
		}


}
