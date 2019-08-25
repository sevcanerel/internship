package com.library.library;
	
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



	public interface PublisherRepository extends JpaRepository<PublisherEntity, Integer> {

		@Query(value="SELECT * FROM publishers",nativeQuery=true)
		public List<PublisherEntity> getAllPublishers();
		@Query(value="SELECT * FROM publishers WHERE name=?1",nativeQuery=true)
		public PublisherEntity getPublisherByName(String name);
	
		@Query(value="SELECT * FROM publishers ORDER BY name ASC",nativeQuery=true)
		public List<PublisherEntity> orderPublishersByName();
   
        
        
        
        
	}
	
	