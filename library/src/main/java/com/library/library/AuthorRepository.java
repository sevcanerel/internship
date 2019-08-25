package com.library.library;
	
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



	public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {

		
        @Query(value="SELECT * FROM authors",nativeQuery=true)
		public List<AuthorEntity> getAllAuthors();
		@Query(value="SELECT * FROM authors WHERE name=?1",nativeQuery=true)
		public AuthorEntity getAuthorByName(String name);
		@Query(value="SELECT * FROM authors ORDER BY first_name ASC",nativeQuery=true)
		public List<AuthorEntity> orderAuthorsByName();
		/*@Query(value="SELECT *  FROM  books  WHERE author_id=?1",nativeQuery=true)
        public List<BookEntity> AuthorsBooks(Integer authorId);*/
	     @Query(value="SELECT DISTINCT authors.* FROM  authors INNER JOIN books ON authors.id =books.author_id WHERE books.publisher_id=?1",nativeQuery=true)
	      public List<AuthorEntity> PublishersAuthors(Integer publisherId);

	}
	
	


