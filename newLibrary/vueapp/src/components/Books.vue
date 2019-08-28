<template>
  <div class="bookAll" style="padding:5px;  margin:20px; ">
    <br />

    <v-btn
      elevation="20"
      color="#35938b"
      style="color:white;"
      v-if="order"
      v-on:click="orderBooks"
    >{{booksOrder}}</v-btn>
    <v-btn
      elevation="20"
      color="#35938b"
      style="color:white;"
      v-if="addbutton"
      v-on:click="newBook"
    >Add Book</v-btn>

    <v-menu offset-x open-on-hover max-height="300px">
      <template v-slot:activator="{ on }">
        <v-btn
          elevation="20"
          color="#35938b"
          style="color:white;"
          v-if="cat"
          dark
          v-on="on"
        >See Categories ></v-btn>
      </template>
      <v-list style="background-color:#EFEBE9;">
        <v-list-item
          v-for="category in categories"
          v-bind:key="category"
          @click="CategoryBooks(category)"
          style="background-color:#EFEBE9;"
        >
          <v-list-item-title style="color:#6D4C41; ">{{ category }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
    <input
      v-if="!hide"
      type="text"
      style="outline-color: #35938b; border:solid #35938b; "
      v-model="search"
      placeholder="Search Book"
    />
    <div v-if="noBooks" style="color:#795548; font-style:bold;">
      <br />
      <br />No Book Is Found
    </div>
    <v-container>
      <ul>
        <v-row no-gutters>
          <li style="list-style-type: none;" v-for="book in filteredBooks" v-bind:key="book.title">
            <v-col v-for="n in 1" :key="n" cols="sm">
              <v-card class="brown lighten-5" width="250px" height="450px">
                <div style="text-align:center; padding-top:10px; margin-left:5px; margin-right:5px;"> <h2 style="font-style:italic; color:#8D6E63; display:inline">{{book.title}}</h2></div>
                  <br />
                <div style="text-align:center;">
                  <img
                    :src="'http://localhost:8090/RestC/download/' + book.bookImg+ '?' + Date.now()"
                    style="height:140px;width:170px;"
                  />
                </div>
                <div style="font-style:italic; color:#8D6E63; text-align:center">
                  
                 Price:
                  <p style="font-style:italic; color:black; display:inline">{{book.price}}</p>
                  <br />Date:
                  <p
                    style="font-style:italic; color:black; display:inline"
                  >{{formatted(book.date.toString())}}</p>
                  <br />Author:
                  <p
                    style="font-style:italic; color:black; display:inline"
                  >{{book.author.firstName}} {{book.author.lastName}}</p>
                  <br />Publisher:
                  <p style="font-style:italic; color:black; display:inline">{{book.publisher.name}}</p>
                  <br />Category:
                  <p style="font-style:italic; color:black; display:inline">{{book.category}}</p>
                </div><div style="text-align:center">
                <v-btn
                  style="color:white;"
                  elevation="20"
                  color="brown lighten-3"
                  v-on:click="deleteBook(book.id,book.title)"
                >delete</v-btn>
                <v-btn
                  style="color:white;"
                  elevation="20"
                  color="#35938b"
                  v-on:click="edit(book.id,book.title,book.date,book.publisher,book.author,book.price,book.bookImg,book.category)"
                >edit</v-btn></div>
                <br />
                <br />
              </v-card>
            </v-col>
          </li>
        </v-row>
      </ul>
    </v-container>
    <br />
    <br />

    <v-dialog width="600" v-model="seeBooks" class="white">
      <v-card >
        <v-card-title class="teal accent-4" primary-title>
          <p style="margin-left:220px; color:white; font-weight: bold;">{{currentCat}}</p>
        </v-card-title>
        <v-card-text>
          <div v-if="noCatBooks" style="color:#795548; font-weight: bold;;margin-left:195px;">
            <br />No Book Is Found
          </div>
          <div style="margin-top:20px;">
            <v-container>
              <ul>
                <v-row no-gutters>
                  <li
                    style="list-style-type:none;"
                    v-for="book in allCatBooks"
                    v-bind:key="book.id"
                  >
                    <v-col v-for="n in 1" :key="n" cols="sm">
                      <v-card class="brown lighten-5"  width="200px" height="300px">
                        <div style="font-style:italic; color:#8D6E63; text-align:center ">
                          <p><div style="text-align:center">
                             <div style="text-align:center; padding-top:10px; margin-left:5px; margin-right:5px;"> <h2 style="font-style:italic;  color:#8D6E63; display:inline">{{book.title}}</h2></div>
                  <br />
                            <img
                              :src="'http://localhost:8090/RestC/download/' + book.bookImg"
                              style="height:100px;width:140px;"
                            /></div>
                         Price:
                          <p style="font-style:italic; color:black; display:inline">{{book.price}}</p>
                          <br />Date:
                          <p
                            style="font-style:italic; color:black; display:inline"
                          >{{formatted(book.date)}}</p>
                          <br />Author:
                          <p
                            style="font-style:italic; color:black; display:inline"
                          >{{book.author.firstName}} {{book.author.lastName}}</p>
                          <br />Publisher:
                          <p
                            style="font-style:italic; color:black; display:inline"
                          >{{book.publisher.name}}</p>
                        </div>
                      </v-card>
                    </v-col>
                  </li>
                </v-row>
              </ul>
            </v-container>
          </div>
        </v-card-text>
        <v-card-actions>
          <v-btn
            style="color:white;"
            elevation="20"
            color="brown lighten-3"
            v-on:click="quitCat"
          >Quit</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="add" class="white">
      <v-card>
        <div class="modal">
          <v-card-text>
            <div v-if="warning" style="color:red;font-style:bold;">THIS BOOK EXISTS</div>
            <div v-if="wrong" style="color:red;font-style:bold;">PLEASE FILL ALL AREAS</div>
            <div v-if="notnum" style="color:red;font-style:bold;">PRICE MUST BE A NUMBER TYPE</div>
            <div class="form-group">
              <label for="title" style="font-style:italic; color:#8D6E63; ">Title:</label>
              <v-text-field type="text" id="title" v-model="book.title" placeholder="Enter Title" />
            </div>
            <div class="form-group">
              <label for="date" style="font-style:italic; color:#8D6E63; ">Date:</label>

              <v-text-field type="date" id="date" v-model="book.date" />
            </div>

            <div class="form-group">
              <label for="price" style="font-style:italic; color:#8D6E63; ">Price:</label>
              <v-text-field id="price" v-model="book.price" placeholder="Enter Price" />
            </div>
            <div class="form-group">
              <label for="category" style="font-style:italic; color:#8D6E63; ">Category:</label>
              <select
                style="background-color:#E8F5E9; outline-color:#A5D6A7;"
                v-model="book.category"
              >
                <option v-for="category in categories" v-bind:key="category">{{category}}</option>
              </select>
            </div>
            <br />
            <div class="form-group">
              <label for="pub" style="font-style:italic; color:#8D6E63; ">Publisher:</label>
              <select
                style="background-color:#E8F5E9; outline-color:#A5D6A7;"
                id="pub"
                v-model="book.publisher"
              >
                <option
                  v-for="publisher in allPublishers"
                  :value="publisher"
                  v-bind:key="publisher.id"
                >Name:{{publisher.name}} Phone:{{publisher.phone}} Address:{{publisher.address}}</option>
              </select>
            </div>

            <br />
            <div class="form-group">
              <label for="aut" style="font-style:italic; color:#8D6E63; ">Author:</label>
              <select
                style="background-color:#E8F5E9; outline-color:#A5D6A7;"
                id="aut"
                v-model="book.author"
              >
                <option
                  v-for="author in allAuthors"
                  :value="author"
                  v-bind:key="author.id"
                >{{author.firstName}} {{author.lastName}}</option>
              </select>
            </div>
            <br />
            <div>
              <label for="fileInput" style="font-style:italic; color:#8D6E63; ">Book Cover:</label>
              <input
                type="file"
                id="fileInput"
                style="display:inline;"
                placeholder="Upload Book Image"
                @change="onFileSelected"
                accept="image/*"
              />
              <br />
              <br />
            </div>

            <div class="view" v-if="imageData.length > 0">
              <img
                style=" height:100px; width: 130px;background-color: white;border: 1px solid #DDD;padding: 5px;"
                class="preview"
                :src="imageData"
              />
            </div>
          </v-card-text>
          <v-card-actions>
            <v-btn
              style="color:white;"
              elevation="20"
              color="#35938b"
              v-on:click="submit(idVar)"
            >Submit</v-btn>
            <v-btn
              style="color:white;"
              elevation="20"
              color="brown lighten-3"
              v-on:click="quitSubmit"
            >Quit</v-btn>
          </v-card-actions>
        </div>
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="edited">
      <v-card class="brown lighten-5">
        <v-card-text style="padding-top:20px;">
          <div v-if="warning" style="color:red;font-style:bold;">THIS BOOK EXISTS</div>
          <div v-if="wrong" style="color:red;font-style:bold;">PLEASE FILL ALL AREAS</div>
          <div v-if="notnum" style="color:red;font-style:bold;">PRICE MUST BE A NUMBER TYPE</div>
          <div class="form-group">
            <label for="title" style="color:#8D6E63; font-style:italic;">Title:</label>
            <v-text-field id="title" v-model="book.title" placeholder="Enter Title" />
          </div>
          <div class="form-group">
            <label for="date" style="color:#8D6E63; font-style:italic;">Date:</label>
            <v-text-field type="date" id="date" v-model="book.date" />
          </div>
          <div class="form-group">
            <label for="price" style="color:#8D6E63; font-style:italic;">Price:</label>
            <v-text-field id="price" v-model="book.price" placeholder="Enter Price" />
          </div>
          <div class="form-group">
            <label for="category" style="color:#8D6E63; font-style:italic;">Category:</label>
            <select
              v-model="book.category"
              style="background-color:#E8F5E9; outline-color:#A5D6A7;"
            >
              <option v-for="category in categories" v-bind:key="category">{{category}}</option>
            </select>
          </div>
          <br />
          <div class="form-group">
            <label for="pub" style="color:#8D6E63; font-style:italic;">Publisher:</label>
            <select
              id="pub"
              v-model="book.publisher"
              style="background-color:#E8F5E9; outline-color:#A5D6A7;"
            >
              <option
                v-for="publisher in allPublishers"
                :value="publisher"
                v-bind:key="publisher.id"
              >Name:{{publisher.name}} Phone:{{publisher.phone}} Address:{{publisher.address}}</option>
            </select>
          </div>
          <br />
          <div class="form-group">
            <label for="aut" style="color:#8D6E63; font-style:italic;">Author:</label>
            <select
              id="aut"
              v-model="book.author"
              style="background-color:#E8F5E9; outline-color:#A5D6A7;"
            >
              <option
                v-for="author in allAuthors"
                :value="author"
                v-bind:key="author.id"
              >{{author.firstName}} {{author.lastName}}</option>
            </select>
          </div>
          <br />
          <div>
            <label for="fileInput" style="color:#8D6E63; font-style:italic;">Book Cover:</label>
            <input id="fileInput" type="file" @change="onFileSelected" accept="image/*" />
            <br />
            <br />
          </div>
          <div v-if="first">
            <img
              style=" height:100px; width: 130px;background-color: white;border: 1px solid #DDD;padding: 5px;"
              class="preview"
              :src="'http://localhost:8090/RestC/download/' + book.bookImg+ '?' + Date.now()"
            />
          </div>
          <div class="view" v-if="imageData.length > 0">
            <img
              style="  height:100px; width: 130px;background-color: white;border: 1px solid #DDD;padding: 5px;"
              class="preview"
              :src="imageData"
            />
          </div>
        </v-card-text>
        <v-card-actions>
          <v-btn
            style="color:white;"
            elevation="20"
            color="#35938b"
            v-on:click="changeBook(idVar)"
          >Change</v-btn>
          <v-btn
            style="color:white;"
            elevation="20"
            color="brown lighten-3"
            v-on:click="quitChange"
          >Quit</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
export default {
  name: "books",
  components: {},
  data() {
    return {
      add: false,
      allAuthors: {},
      image: "",
      show: true,
      seeCat: false,
      search: "",
      imageData: "",
      cat: false,
      noCatBooks: false,
      order: false,
      addbutton: true,
      allPublishers: {},
      first: true,
      allCatBooks: [],
      booksOrder: "Order By Price",
      warning: false,
      notnum: false,
      whichOrder: false,
      seeBooks: false,
      noBooks: false,
      uptadePicture: true,
      currentCat: "",
      booksTitle: "",
      bookAll: {},
      hide: true,
      categories: [
        "Art",
        "Biography",
        "Business",
        "Comedy",
        "Comics",
        "Cooking",
        "Drama",
        "Educational",
        "Entertainment",
        "Health",
        "History",
        "Home&Garden",
        "Horror",
        "Kids",
        "Literature",
        "Play",
        "Poetry",
        "Religion",
        "Romance",
        "Self-Help",
        "Sci-Fi",
        "Social Sciences",
        "Sports",
        "Technology",
        "Travel"
      ],
      msg: "Show Books",
      allBooks: [],
      edited: false,
      tempImage: "",
      wrong: false,
      idVar: 0,
      reload: false,
      book: {
        title: "",
        price: 0,
        category: "",
        bookImg: "",
        date: new Date().toISOString().substr(0, 10),
        author: {},
        publisher: {}
      },
      imgChange: false
    }; //return
  }, //data
  methods: {
    onFileSelected: function(event) {
      var input = event.target;
      this.first = false;

      if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = e => {
          this.tempImage = input.files[0];
          this.imageData = e.target.result;
        };

        reader.readAsDataURL(input.files[0]);
      }
    },
    quitCat: function() {
      this.seeCat = false;
      this.edited = false;
      this.add = false;
      this.show = true;
      this.hide = false;
      this.order = true;
      this.addbutton = true;
      this.showBooks();
      this.noBooks = false;
      this.noCatBooks = false;
      this.seeBooks = false;
      this.cat = true;
    },

    seeCategories: function() {
      this.noBooks = false;
      this.seeCat = true;
      this.cat = false;
      this.edited = false;
      this.add = false;
      this.show = false;
      this.hide = true;
      this.order = false;
      this.addbutton = false;
      this.allBooks = [];
      this.noCatBooks = false;
    },
    CategoryBooks: function(cat) {
      this.$http
        .get("http://localhost:8090/RestC/CategoryBooks/" + cat)
        .then(response => {
          this.allCatBooks = response.data;
          this.seeBooks = true;
          this.currentCat = cat;
          var deneme = JSON.stringify(response.data);
          if (deneme === "[]") {
            this.noCatBooks = true;
          } else {
            this.noCatBooks = false;
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    formatted: function(date) {
      var formatDate = date.substring(0, 10);
      return formatDate;
    },
    quitSubmit: function() {
      this.addbutton = true;
      this.add = false;
      this.hide = true;
      this.warning = "";
      this.errorr = false;
      this.wrong = false;
      this.notnum = false;
      this.noBooks = false;
      this.imageData = "";
      this.tempImage = "";
      this.cat = true;
      this.showBooks();
    },

    quitChange: function() {
      this.addbutton = true;
      this.warning = false;
      this.edited = false;
      this.hide = true;

      this.wrong = false;
      this.notnum = false;
      this.imgChange = false;
      this.noBooks = false;
      this.cat = true;
      this.showBooks();
    },
    showBooks: function() {
      if (this.whichOrder == false) {
        this.$http
          .get("http://localhost:8090/RestC/orderBooksByName")
          .then(response => {
            this.hide = false;
            this.order = true;
            this.show = false;
            this.cat = true;
            this.allBooks = response.data;
            var deneme = JSON.stringify(response.data);
            if (deneme === "[]") {
              this.noBooks = true;
            } else {
              this.noBooks = false;
            }
          })
          .catch(err => {
            console.log(err);
          });
      } else {
        this.$http
          .get("http://localhost:8090/RestC/orderBooksByPrice")
          .then(response => {
            this.hide = false;
            this.order = true;
            this.show = false;
            this.cat = true;
            this.allBooks = response.data;
            var deneme = JSON.stringify(response.data);
            if (deneme === "[]") {
              this.noBooks = true;
            } else {
              this.noBooks = false;
            }
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    orderBooks: function() {
      this.hide = false;

      this.show = false;
      if (this.whichOrder == false) {
        this.$http
          .get("http://localhost:8090/RestC/orderBooksByPrice")
          .then(response => {
            this.allBooks = response.data;
            this.cat = true;
            this.whichOrder = true;
            this.booksOrder = "Order By Name";
          })
          .catch(err => {
            console.log(err);
          });
      } else {
        this.$http
          .get("http://localhost:8090/RestC/orderBooksByName")
          .then(response => {
            this.allBooks = response.data;
            this.cat = true;
            this.whichOrder = false;
            this.booksOrder = "Order By Price";
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    deleteBook: function(id, name) {
      this.order = false;
      this.$http
        .delete("http://localhost:8090/RestC/deleteBookById/" + id.toString())
        .then(response => {
          this.$http
            .delete("http://localhost:8090/RestC/deleteFile/" + name + ".jpg")
            .then(response => {
              console.log(response);
            })
            .catch(err => {
              console.log(err);
            });
          this.showBooks();
        })
        .catch(err => {
          console.log(err);
        });
    },
    edit: function(
      id,
      title1,
      date1,
      publisher1,
      author1,
      price1,
      bookImg1,
      category1
    ) {
      this.book.bookImg = "";
      this.uptadePicture = false;
      this.booksTitle = title1;
      this.first = true;
      var tempPublisher = JSON.parse(JSON.stringify(publisher1));
      var tempAuthor = JSON.parse(JSON.stringify(author1));
      var date2 = this.formatted(date1);
      this.book = {
        title: title1,
        price: price1,
        category: category1,
        bookImg: bookImg1,
        date: date2,
        author: tempAuthor,
        publisher: tempPublisher
      };

      this.noBooks = false;

      this.edited = true;
      this.idVar = id;

      this.$http
        .get("http://localhost:8090/RestC/getAllAuthors")
        .then(response => {
          this.allAuthors = response.data;
        })
        .catch(err => {
          console.log(err);
        });
      this.$http
        .get("http://localhost:8090/RestC/getAllPublishers")
        .then(response => {
          this.allPublishers = response.data;
        })
        .catch(err => {
          console.log(err);
        });
      this.imageData = "";
    },
    newBook: function() {
      this.add = true;

      this.book = {
        title: "",
        price: "",
        category: "",
        date: "",
        author: {},
        publisher: {}
      };
      this.imageData = "";
      this.$http
        .get("http://localhost:8090/RestC/getAllAuthors")
        .then(response => {
          this.allAuthors = response.data;
        })
        .catch(err => {
          console.log(err);
        });
      this.$http
        .get("http://localhost:8090/RestC/getAllPublishers")
        .then(response => {
          this.allPublishers = response.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    submit: function(id) {
      this.wrong = false;
      this.noBooks = false;
      this.warning = false;
      this.notnum = false;
      var tempAuthor = this.book.author;
      var tempPublisher = this.book.publisher;

      if (typeof tempAuthor === "string" || tempAuthor instanceof String) {
        console.log(this.book.author);
        tempAuthor = JSON.parse(this.book.author);
      }
      if (
        typeof tempPublisher === "string" ||
        tempPublisher instanceof String
      ) {
        var tempPublisher = JSON.parse(this.book.publisher);
      }

      var data = {
        title: this.book.title,
        price: this.book.price,
        category: this.book.category,
        date: this.book.date,
        author: tempAuthor,
        publisher: tempPublisher,
        bookImg: this.book.title + ".jpg"
      };

      let header = {
        "Content-Type": "multipart/form-data"
      };
      var formData = new FormData();
      var blob = this.tempImage.slice(0, this.tempImage.size, "image/jpg");
      var fileTemp = new File([blob], this.book.title + ".jpg", {
        type: "image/jpg"
      });
      formData.append("file", fileTemp);
      var theBooks = {};
      this.$http
        .get("http://localhost:8090/RestC/getAllBooks")
        .then(response => {
          this.theBooks = response.data;
          if (
            fileTemp.size == 0 ||
            this.book.price === "" ||
            (this.book.title == "" || this.book.title == null) ||
            JSON.stringify(this.book.publisher) == "{}" ||
            JSON.stringify(this.book.author) == "{}" ||
            (this.book.date == "" || this.book.date == null) ||
            (this.book.category == "" || this.book.category == null)
          ) {
            this.wrong = true;
          }

          if (Number(this.book.price).toString() == "NaN") {
            this.notnum = true;
          }
          var i = 0;
          for (i; i < this.theBooks.length; i++) {
            if (this.theBooks[i].title.trim() == this.book.title.trim()) {
              this.warning = true;
            } else {
              if (
                this.theBooks[i].publisher.name.trim() ==
                  tempPublisher.name.trim() &&
                this.theBooks[i].publisher.address.trim() ==
                  tempPublisher.address.trim() &&
                this.theBooks[i].publisher.phone.trim() ==
                  tempPublisher.phone.trim() &&
                this.theBooks[i].author.lastName.trim() ==
                  tempAuthor.lastName.trim() &&
                this.theBooks[i].author.firstName.trim() ==
                  tempAuthor.firstName.trim() &&
                this.theBooks[i].title.trim() == this.book.title.trim() &&
                this.theBooks[i].price.toString().trim() ==
                  this.book.price.trim() &&
                this.theBooks[i].date ==
                  this.book.date + "T00:00:00.000+0000" &&
                this.theBooks[i].category == this.book.category &&
                this.theBooks[i].bookImg == this.book.bookImg
              ) {
                this.warning = true;
              }
            }
          }

          if (!this.warning && !this.wrong && !this.notnum) {
            this.$http
              .post("http://localhost:8090/RestC/addNewBook", data)
              .then(response => {
                console.log(response.data);

                if (fileTemp.size == 0) {
                  this.wrong = true;
                } else {
                  this.$http
                    .post("http://localhost:8090/RestC/upload-file", formData, {
                      header
                    })
                    .then(response => {
                      console.log(response.data);
                      this.showBooks();
                      this.imageData = "";
                      this.tempImage = "";
                    })
                    .catch(err => {
                      console.log(err);
                    });
                }
                this.show = true;
                this.addbutton = true;
                this.add = false;
                this.order = false;
              })
              .catch(err => {
                console.log(err);
              });
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    changeBook: function(id) {
      this.warning = false;
      this.wrong = false;
      this.notnum = false;
      var tempAuthor = this.book.author;
      var tempPublisher = this.book.publisher;
      if (typeof tempAuthor === "string" || tempAuthor instanceof String) {
        tempAuthor = JSON.parse(this.book.author);
      }
      if (
        typeof tempPublisher === "string" ||
        tempPublisher instanceof String
      ) {
        var tempPublisher = JSON.parse(this.book.publisher);
      }

      var data = {
        title: this.book.title,
        price: this.book.price,
        category: this.book.category,
        date: this.book.date,
        author: tempAuthor,
        publisher: tempPublisher,
        bookImg: this.book.title + ".jpg"
      };
      var theBooks = {};

      this.$http
        .get("http://localhost:8090/RestC/getAllBooks")
        .then(response => {
          this.theBooks = response.data;
          var i = 0;

          for (i; i < this.theBooks.length; i++) {
            if (
              this.theBooks[i].title.trim() == this.book.title.trim() &&
              this.theBooks[i].id != id
            ) {
              this.warning = true;
              break;
            }
          }
          if (
            this.book.price === "" ||
            (this.book.title == "" || this.book.title == null) ||
            JSON.stringify(this.book.publisher) == "{}" ||
            JSON.stringify(this.book.author) == "{}" ||
            (this.book.date == "" || this.book.date == null) ||
            (this.book.category == "" || this.book.category == null)
          ) {
            this.wrong = true;
          }
          if (
            Number(this.book.price).toString() == "NaN" ||
            Number(this.book.price).toString() == ""
          ) {
            this.notnum = true;
          }

          if (!this.wrong && !this.notnum && !this.warning) {
            this.order = false;
            this.edited = false;
            this.$http
              .put(
                "http://localhost:8090/RestC/uptadeBookById/" + id.toString(),
                data
              )
              .then(response => {
                console.log(response.data);

                this.book.bookImg = this.book.title + ".jpg";
                this.$http
                  .put(
                    "http://localhost:8090/RestC/changeFileName/" +
                      this.booksTitle +
                      ".jpg" +
                      "/" +
                      this.book.bookImg
                  )
                  .then(response => {
                    if (this.tempImage.size != undefined) {
                      let header = {
                        "Content-Type": "multipart/form-data"
                      };
                      var formData = new FormData();
                      var blob = this.tempImage.slice(
                        0,
                        this.tempImage.size,
                        "image/jpg"
                      );
                      var fileTemp = new File(
                        [blob],
                        this.book.title + ".jpg",
                        {
                          type: "image/jpg"
                        }
                      );
                      formData.append("file", fileTemp);

                      console.log(response);

                      this.$http
                        .delete(
                          "http://localhost:8090/RestC/deleteFile/" +
                            this.book.title +
                            ".jpg"
                        )
                        .then(response => {
                          console.log(response);
                          this.$http
                            .post(
                              "http://localhost:8090/RestC/upload-file",
                              formData,
                              {
                                header
                              }
                            )
                            .then(response => {
                              console.log(response.data);
                              this.tempImage = "";
                              this.book.publisher = {};
                              this.book.author = {};
                              this.uptadePicture = true;
                              this.cat = true;

                              this.addbutton = true;

                              this.showBooks();
                              this.show = true;
                            })
                            .catch(err => {
                              console.log(err);
                            });
                        })
                        .catch(err => {
                          console.log(err);
                        });
                    } else {
                      this.tempImage = "";
                      this.book.publisher = {};
                      this.book.author = {};
                      this.uptadePicture = true;
                      this.cat = true;
                      this.addbutton = true;

                      this.showBooks();
                      this.show = true;
                    }

                    console.log(response.data);
                  })
                  .catch(err => {
                    console.log(err);
                  });
              })
              .catch(err => {
                console.log(err);
              });
          } //if-warning
        })
        .catch(err => {
          console.log(err);
        });
    }
  }, //methods
  mounted() {
    this.showBooks();
  },

  computed: {
    reloadHelper() {
      if (reload == true) {
        this.reload = false;
      }
    },
    filteredBooks: function() {
      return this.allBooks.filter(book => {
        var titleBook = book.title;
        titleBook = titleBook.toLowerCase();
        return titleBook.match(this.search.toLowerCase());
      });
    }
  }
}; //export default
</script>
<style scoped>
.bookAll.modal {
  padding: 20px;
}
.bookAll.modal.form-group {
  margin: 10px;
}
.bookAll.edited {
  padding: 20px;
}
.bookAll.edited.form-group {
  margin: 10px;
}
::-webkit-file-upload-button {
  background: #35938b;
  color: white;
}
</style>
