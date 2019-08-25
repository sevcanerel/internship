package com.library.library;


import com.library.library.FileResponse;
import com.library.library.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController; 
	@CrossOrigin
	@RestController
	@RequestMapping("/RestC")	
    public class RestC {
		@Autowired 
		AuthorService authorService;
		@Autowired 
		BookService bookService;
		@Autowired 
		PublisherService publisherService;
		@Autowired 
	    private StorageService storageService;
		@Autowired
	    private ServletContext servletContext;
		
		//StorageController--------------------------------------------------------------------------------------------------------------------------------
		  public RestC(StorageService storageService) {
		        this.storageService = storageService;
		    }

		    @GetMapping("/listAllFiles")
		    public String listAllFiles(Model model) {

		        model.addAttribute("files", storageService.loadAll().map(
		                path -> ServletUriComponentsBuilder.fromCurrentContextPath()
		                        .path("/download/")
		                        .path(path.getFileName().toString())
		                        .toUriString())
		                .collect(Collectors.toList()));

		        return "listFiles";
		    }

		    
		
		    
		    @GetMapping("/download/{filename}")
		    @ResponseBody
		    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

		        Resource resource = storageService.loadAsResource(filename);
 		        return ResponseEntity.ok().body(resource);
		    }

		    @RequestMapping(value="/upload-file",method=RequestMethod.POST)
		    @ResponseBody
		    public FileResponse uploadFile(@RequestBody MultipartFile file) {
		        String name = storageService.store(file);
            
		        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
		                .path("/download/")
		                .path(name)
		                .toUriString();

		        return new FileResponse(name, uri, file.getContentType(), file.getSize());
		    }
		    @RequestMapping(value="/deleteFile/{fileName}",method=RequestMethod.DELETE)
		    public void deleteFile(@PathVariable String fileName){
		    	
		    	   storageService.deleteFile(fileName);
		    	
		    }
		    @RequestMapping(value="/changeFileName/{oldFileName}/{newFileName}",method=RequestMethod.PUT)
		    public void changeFileName(@PathVariable String oldFileName,@PathVariable String newFileName){
		    	   
		    	   storageService.changeFileName(oldFileName,newFileName);
		    	
		    }
		    @PostMapping("/upload-multiple-files")
		    @ResponseBody
		    public List<FileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		        return Arrays.stream(files)
		                .map(file -> uploadFile(file))
		                .collect(Collectors.toList());
		    }
		
		//AuthorController---------------------------------------------------------------------------------------------------------------------------
		@RequestMapping(value="/getAllAuthors",method=RequestMethod.GET)
	    public List<AuthorEntity> getAllAuthors(){
			
			return authorService.repository.getAllAuthors();
			
			
			
		}
		@RequestMapping(value="/deleteAllAuthors",method=RequestMethod.DELETE)
		public void deleteAllAuthors(){
			authorService.repository.deleteAll();
		}
		@GetMapping("/getAuthorById/{i}")
	    public Optional<AuthorEntity> getAuthorById(@PathVariable int i){
	    	if(authorService.repository.findById(i)!=null ){
	    	return authorService.getAuthorById(i);}
	    	return null;
	    
	    	
	    }
		
	
		@RequestMapping(value="/deleteAuthorById/{i}",method=RequestMethod.DELETE)
		public void deleteAuthorById(@PathVariable int i){
			
			bookService.repository.deleteBookByAuthorId(i);
			authorService.deleteAuthorById(i);
		}
		@RequestMapping(value="/addNewAuthor",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
		public void addNewAuthor(@RequestBody AuthorEntity author){
			authorService.addNewAuthor(author);
		}
		@RequestMapping(value="/uptadeAuthorById/{i}",method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
		public void uptadeAuthor(@RequestBody AuthorEntity author,@PathVariable int i){
			authorService.uptadeAuthor( author,i);
		}
		@RequestMapping(value="/orderAuthorsByName",method=RequestMethod.GET)
		public List<AuthorEntity> orderAuthorsByName(){
			
		  return authorService.repository.orderAuthorsByName();
		}
		  @RequestMapping(value="/PublishersAuthors/{publisherId}",method=RequestMethod.GET)
	      public List<AuthorEntity> PublishersAuthors(@PathVariable Integer publisherId){
				  return authorService.repository.PublishersAuthors(publisherId);
				  
				  
			  }
			  
		
		
		//BookController----------------------------------------------------------------------------------------------------------------
		
		
		@RequestMapping(value="/getAllBooks",method=RequestMethod.GET)
	    public List<BookEntity> getAllBooks(){
	    	
	    	return bookService.getAllBooks();
	    	
	    	
	    }
		@RequestMapping(value="/CategoryBooks/{category}",method=RequestMethod.GET)
	    public List<BookEntity> CategoryBooks(@PathVariable String category){
	    	
	    	return bookService.repository.CategoryBooks(category);
	    	
	    	
	    }
		
		@GetMapping("/getBookById/{i}")
	    public Optional<BookEntity> getBookById(@PathVariable int i){
	    	
	    	return bookService.getBookById(i);
	    
	    	
	    }
		@RequestMapping(value="/deleteAllBooks",method=RequestMethod.DELETE)
		public void deleteAllBooks(){
			bookService.deleteAllBooks();
		}
		@RequestMapping(value="/deleteBookById/{i}",method=RequestMethod.DELETE)
		public void deleteBookById(@PathVariable int i){
			bookService.deleteBookById(i);
		}
		@RequestMapping(value="/addNewBook",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
		public void addNewBook(@RequestBody BookEntity book){
			bookService.addNewBook(book);
		}
		@RequestMapping(value="/uptadeBookById/{id}",method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
		public void uptadeBook(@RequestBody BookEntity book,@PathVariable int id){
			bookService.uptadeBook( book,id);
		}
		
		@RequestMapping(value="/orderBooksByPrice",method=RequestMethod.GET)
		public List<BookEntity> orderBooksByPrice(){
			
			return bookService.repository.orderBooksByPrice();
		}
		
		@RequestMapping(value="/orderBooksByName",method=RequestMethod.GET)
		public List<BookEntity> orderBooksByName(){
			return bookService.repository.orderBooksByName();
			
		}
		@CrossOrigin
		@RequestMapping(value="/AuthorsBooks/{authorId}",method=RequestMethod.GET)
		public List<BookEntity> AuthorsBooks(@PathVariable Integer authorId){
			
			return bookService.repository.AuthorsBooks(authorId);
			
		}
		 @RequestMapping(value="/PublishersBooks/{publisherId}",method=RequestMethod.GET)
			 public List<BookEntity> PublishersBooks(@PathVariable Integer publisherId){
				 return bookService.repository.PublishersBooks(publisherId);
				 
			 }
		
		
		//PubliherController----------------------------------------------------------------------------------------------------------------
		
		
				@RequestMapping(value="/getAllPublishers",method=RequestMethod.GET)
			    public List<PublisherEntity> getAllPublishers(){
					return publisherService.repository.getAllPublishers();
			    	
			    	
			    	
			    	
			    }
				@RequestMapping(value="/deleteAllPublishers",method=RequestMethod.DELETE)
				public void deleteAllPublishers(){
					publisherService.repository.deleteAll();
				}
				@GetMapping("/getPublisherById/{i}")
			    public Optional<PublisherEntity> getPublisherById(@PathVariable int i){
			    	
			    	return publisherService.getPublisherById(i);
			    
			    	
			    }
				
				@RequestMapping(value="/deletePublisherById/{i}",method=RequestMethod.DELETE)
				public void deletePublisherById(@PathVariable int i){
					bookService.repository.deleteBookByPublisherId(i);
					publisherService.deletePublisherById(i);
					
				}
				@RequestMapping(value="/addNewPublisher",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
				public void addNewPublisher(@RequestBody PublisherEntity publisher){
					publisherService.addNewPublisher(publisher);
				}
				@RequestMapping(value="/uptadePublisher/{i}",method=RequestMethod.PUT,consumes=MediaType.APPLICATION_JSON_VALUE)
				public void uptadePublisher(@RequestBody PublisherEntity publisher,@PathVariable int i){
					publisherService.uptadePublisher(publisher,i);
				}


				@RequestMapping(value="/orderPublishersByName",method=RequestMethod.GET)
				public List<PublisherEntity> orderPublishersByName(){
					
				  return publisherService.repository.orderPublishersByName();
					
					
				}
				public StorageService getStorageService() {
					return storageService;
				}
				public void setStorageService(StorageService storageService) {
					this.storageService = storageService;
				}
		
      			
      			
		

	}


