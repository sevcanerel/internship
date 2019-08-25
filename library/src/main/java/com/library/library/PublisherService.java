package com.library.library;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

	@Autowired 
	PublisherRepository repository;
	
	
	
	
	 public Optional<PublisherEntity> getPublisherById(int i){
	    	
	    	return repository.findById(i);
	    
	    	
	    }
	 
	 public void deletePublisher(PublisherEntity publisher){
			
			int i=publisher.getId();
			
			repository.deleteById(i);
			
		}
	
	 public void deletePublisherById(int i){
			
		 repository.deleteById(i);
		}
	 
	 public void addNewPublisher(PublisherEntity publisher){
	
			repository.save(publisher);
			
		}
	 
	 
	 
	 
	 public void uptadePublisher(PublisherEntity publisher,int i){
		 PublisherEntity publisherFound=repository.findById(i).get();
		 publisherFound.setAddress(publisher.getAddress());
		 publisherFound.setName(publisher.getName());
		 publisherFound.setPhone(publisher.getPhone());
		 
		repository.save(publisherFound);


			
	 
	 }
}
