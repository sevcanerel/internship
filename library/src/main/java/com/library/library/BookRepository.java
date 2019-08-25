package com.library.library;
	


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;




	public interface BookRepository extends JpaRepository<BookEntity, Integer> {
		@Query(value="SELECT * FROM books WHERE author_id = ?1",nativeQuery=true)
		public Optional<BookEntity> getBookByAuthorId(int i);
		@Query(value="SELECT name FROM authors WHERE id = ?1",nativeQuery=true)
		public String getAuthorNameOfBook(int authorId);
		@Query(value="SELECT name FROM publishers WHERE id = ?1",nativeQuery=true)
		public String getPublisherNameOfBook(int publisherId);
		@Transactional
		@Modifying
		@Query(value="DELETE FROM books WHERE author_id = ?1",nativeQuery=true)
		 public void deleteBookByAuthorId( int i);
		@Transactional
		@Modifying
		@Query(value="DELETE FROM books WHERE publisher_id = ?1",nativeQuery=true)
		public void deleteBookByPublisherId( int i);
		@Query(value="SELECT * FROM books ORDER BY price ASC",nativeQuery=true)
		public List<BookEntity> orderBooksByPrice();
		@Query(value="SELECT * FROM books ORDER BY title ASC",nativeQuery=true)
		public List<BookEntity> orderBooksByName();
		@Query(value="SELECT *  FROM  books  WHERE author_id=?1",nativeQuery=true)
        public List<BookEntity> AuthorsBooks(Integer authorId);
		@Query(value="SELECT *  FROM  books  WHERE publisher_id=?1",nativeQuery=true)
        public List<BookEntity> PublishersBooks(Integer publisherId);
		@Query(value="SELECT *  FROM  books  WHERE category=?1",nativeQuery=true)
        public List<BookEntity> CategoryBooks(String category);

	}
	
	