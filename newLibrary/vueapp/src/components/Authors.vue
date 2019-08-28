<template >
  <div class="authors">
    <v-btn
      v-if="add"
      v-on:click="addNewAuthor"
      elevation="20"
      color="#35938b"
      small
      style="color:white;"
    >Add Author</v-btn>

    <input
      v-if="!hide"
      type="text"
      style="outline-color: #35938b; border:solid #35938b; "
      v-model="search"
      placeholder="Search Author"
    />
    <v-dialog width="600" v-model="edited" class="white">
      <v-card>
        <div class="edit">
          <div v-if="wrong" style="color:red;font-style:bold;">PLEASE FILL ALL AREAS</div>

          <div class="fn" style="margin-bottom:20px; margin-left:20px; margin-right:20px;">
            <label style="font-style:italic; color:#8D6E63; " for="firstName">First Name:</label>
            <v-text-field type="text" id="firstName" v-model="author.firstName" />
          </div>
          <div class="ln" style="margin:20px">
            <label style="font-style:italic; color:#8D6E63; " for="lastName">Last Name:</label>
            <v-text-field id="lastName" v-model="author.lastName" />
          </div>
          <v-card-actions>
            <v-btn
              small
              v-on:click="changeAuthor(idVar)"
              elevation="20"
              color="#35938b"
              style="color:white;"
            >Change</v-btn>
            <v-btn
              small
              v-on:click="changeQuit"
              style="color:white;"
              elevation="20"
              color="brown lighten-3"
            >Quit</v-btn>
          </v-card-actions>
        </div>
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="submitted" class="white">
      <v-card>
        <div v-if="submitted" class="add">
          <div v-if="warning" style="color:red;font-style:bold;">THIS AUTHOR EXISTS</div>
          <div v-if="wrong" style="color:red;font-style:bold;">PLEASE FILL ALL AREAS</div>
          <div class="form-group">
            <label
              style="font-style:italic; color:#8D6E63; margin-left:5px;"
              for="firstName"
            >First Name:</label>
            <v-text-field
              style="margin-bottom:20px; margin-left:20px; margin-right:20px;"
              id="firstName"
              v-model="author.firstName"
            />
          </div>
          <div class="form-group">
            <label
              style="font-style:italic; color:#8D6E63; margin-left:5px;"
              for="firstName"
            >Last Name:</label>
            <v-text-field style="margin:20px" id="lastName" v-model="author.lastName" />
          </div>
          <v-card-actions>
            <v-btn
              small
              v-on:click="submitAuthor"
              elevation="20"
              color="#35938b"
              style="color:white;"
            >Submit</v-btn>
            <v-btn
              small
              v-on:click="submitQuit"
              style="color:white;"
              elevation="20"
              color="brown lighten-3"
            >Quit</v-btn>
          </v-card-actions>
        </div>
      </v-card>
    </v-dialog>
    <div v-if="noAuthors" style="color:#795548; font-weight:bold;">
      <br />
      <br />No Author Is Found
    </div>
    <v-container>
      <ul>
        <v-row no-gutters>
          <li
            style="list-style-type: none;"
            v-for="author in filteredAuthors"
            v-bind:key="author.id"
          >
            <v-col v-for="n in 1" :key="n" cols="sm">
              <v-card class="brown lighten-5">
                <br />
                <br />
                <div
                  style="margin-left:70px; margin-bottom:30px;font-style:italic; color:#8D6E63; "
                >
                  First Name:
                  <p style="font-style:italic; color:black; display:inline">{{author.firstName}}</p>
                  <br />Last Name:
                  <p style="font-style:italic; color:black; display:inline">{{author.lastName}}</p>
                </div>

                <v-btn
                  small
                  v-on:click="deleteAuthor(author.id)"
                  style="color:white;"
                  elevation="20"
                  color="brown lighten-3"
                >delete</v-btn>
                <v-btn
                  small
                  v-on:click="edit(author.id,author.firstName,author.lastName)"
                  elevation="20"
                  color="#35938b"
                  style="color:white;"
                >edit</v-btn>
                <v-btn
                  small
                  v-on:click="authorBook(author.id,author.firstName,author.lastName)"
                  elevation="20"
                  color="#35938b"
                  style="color:white;"
                >Book(s)</v-btn>
              </v-card>
            </v-col>
          </li>
        </v-row>
      </ul>
    </v-container>
    <v-dialog width="600" v-model="displayBooks" class="white">
      <v-card>
        <div class="books">
          <div
            style="padding-bottom:20px;color:white; font-weight: bold; background-color:#00BFA5;"
          >
            <h2 style="display:inline;margin-left:230px; margin-top:50px; ">{{authorName}}</h2>
          </div>
          <div
            v-if="noBooks"
            style="color:#795548; margin-left:230px; font-style:bold;"
          >No Book Is Found</div>
          <div class="showbooks">
            <v-container>
              <ul>
                <v-row no-gutters>
                  <li style="list-style-type: none;" v-for="book in allBooks" v-bind:key="book.id">
                    <v-col>
                      <v-card class="brown lighten-5" width="250px" height="400px">
                        <div style="font-style:italic; color:#8D6E63; text-align:center;">
                          <div
                            style="text-align:center; padding-top:10px; margin-left:5px; margin-right:5px;"
                          >
                            <h2
                              style="font-style:italic; color:#8D6E63; display:inline"
                            >{{book.title}}</h2>
                          </div>
                          <br />
                          <div style="text-align:center">
                            <img
                              :src="'http://localhost:8090/RestC/download/' + book.bookImg"
                              style="height:100px;width:140px;"
                            />
                          </div>
                          <br />Price:
                          <p style="font-style:italic; color:black; display:inline">{{book.price}}</p>
                          <br />Date:
                          <p
                            style="font-style:italic; color:black; display:inline"
                          >{{formatted(book.date)}}</p>

                          <br />Publisher:
                          <p
                            style="font-style:italic; color:black; display:inline"
                          >{{book.publisher.name}}</p>
                          <br />Category:
                          <p
                            style="font-style:italic; color:black; display:inline"
                          >{{book.category}}</p>
                        </div>
                      </v-card>
                    </v-col>
                  </li>
                </v-row>
              </ul>
            </v-container>
          </div>
          <v-btn
            small
            v-on:click="quitBooks"
            style="color:white; margin-left:535px;"
            elevation="20"
            color="brown lighten-3"
          >Quit</v-btn>
        </div>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  name: "authors",
  data() {
    return {
      msg: "Show Authors",
      hide: true,
      show: true,
      add: true,
      submitted: false,
      displayBooks: false,
      search: "",
      noBooks: false,
      noAuthors: false,
      idVar: 0,
      wrong: false,
      edited: false,
      allAuthors: [],
      allBooks: [],
      author: { firstName: "", lastName: "" },
      allPublishers: [],
      authorsBooks: [],
      authorsAll: [],
      warning: false,
      authorName: ""
    };
  },
  methods: {
    formatted: function(date) {
      var formatDate = date.substring(0, 10);
      return formatDate;
    },
    showAuthors: function() {
      this.msg = "reload";
      this.hide = false;
      this.show = false;
      this.$http
        .get("http://localhost:8090/RestC/orderAuthorsByName")
        .then(response => {
          this.allAuthors = response.data;
          var deneme = JSON.stringify(response.data);
          if (deneme === "[]") {
            this.noAuthors = true;
          } else {
            this.noAuthors = false;
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    quitBooks: function() {
      this.add = true;
      this.displayBooks = false;
      this.allBooks = {};
      this.showAuthors();
      this.noBooks = false;
    },
    authorBook: function(id, firstName, lastName) {
      this.displayBooks = true;
      this.authorName = firstName + " " + lastName;
      this.$http
        .get("http://localhost:8090/RestC/AuthorsBooks/" + id.toString())
        .then(response => {
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
    },
    submitQuit: function() {
      this.add = true;
      this.noAuthors = false;
      this.wrong = false;
      this.warning = false;
      this.hide = true;
      this.show = true;
      this.submitted = false;
      this.showAuthors();
    },
    changeQuit: function() {
      this.hide = true;
      this.wrong = false;
      this.warning = false;
      this.noAuthors = false;
      this.edited = false;
      this.add = true;
      this.showAuthors();
    },
    hideAuthors: function() {
      this.show = true;
      this.hide = true;
      this.allAuthors = [];
      this.msg = "Show Authors";
      this.noAuthors = false;
    },
    submitAuthor: function() {
      var data = {
        firstName: this.author.firstName,
        lastName: this.author.lastName
      };

      this.$http
        .get("http://localhost:8090/RestC/getAllAuthors")
        .then(response => {
          this.authorsAll = response.data;
          for (var i = 0; i < this.authorsAll.length; i++) {
            if (
              this.authorsAll[i].firstName.trim() ==
                this.author.firstName.trim() &&
              this.authorsAll[i].lastName.trim() == this.author.lastName.trim()
            ) {
              this.warning = true;
              break;
            }
          }

          if (this.author.firstName == "" || this.author.lastName == "") {
            this.wrong = true;
          }
          if (
            this.author.firstName == "" ||
            this.author.firstName == null ||
            (this.author.lastName == "" || this.author.lastName == null)
          ) {
            this.wrong = true;
          } else {
            if (!this.warning && !this.wrong) {
              this.add = true;
              this.submitted = false;
              this.$http
                .post("http://localhost:8090/RestC/addNewAuthor", data)
                .then(response => {
                  console.log(response.data);
                  this.showAuthors();
                })
                .catch(err => {
                  console.log(err);
                });
            }
          }
        })
        .catch(err => {});
      this.warning = false;
      this.wrong = false;
      this.noAuthors = false;
    },
    changeAuthor: function(id) {
      this.warning = false;
      this.noAuthors = false;
      this.wrong = false;
      var data = {
        firstName: this.author.firstName,
        lastName: this.author.lastName
      };

      if (
        this.author.firstName == "" ||
        this.author.firstName == null ||
        (this.author.lastName == "" || this.author.lastName == null)
      ) {
        this.wrong = true;
      } else {
        if (!this.wrong) {
          this.edited = false;
          this.$http
            .put(
              "http://localhost:8090/RestC/uptadeAuthorById/" + id.toString(),
              data
            )
            .then(response => {
              this.showAuthors();
              console.log(response.data);
            })
            .catch(err => {
              console.log(err);
            });
          this.add = true;
        }
      }
    },
    deleteAuthor: function(id) {
      this.noAuthors = false;
      this.$http
        .delete("http://localhost:8090/RestC/deleteAuthorById/" + id.toString())
        .then(response => {
          console.log(response.data);
          this.showAuthors();
        })
        .catch(err => {
          console.log(err);
        });
    },
    edit: function(id, firstName1, lastName1) {
      this.edited = true;

      this.author = {
        firstName: firstName1,
        lastName: lastName1
      };

      this.idVar = id;
    },
    addNewAuthor: function() {
      this.noAuthors = false;
      this.show = true;
      this.submitted = true;

      this.author = { firstName: "", lastName: "" };
    }
  }, //methods
  mounted() {
    this.showAuthors();
  },
  computed: {
    filteredAuthors: function() {
      return this.allAuthors.filter(author => {
        var nameFirst = author.firstName;
        var nameSecond = author.lastName;
        var join = nameFirst.toLowerCase() + " " + nameSecond.toLowerCase();
        return join.match(this.search.toLowerCase());
      });
    }
  }
}; //export default
</script>
<style >
.authors {
  padding: 20px;
  margin: 20px;
}

.authors.edited {
  padding: 20px;
}
.authors.edited.fn {
  margin: 10px;
}
.authors.edited.ln {
  margin: 10px;
}
.authors.submitted {
  padding: 20px;
}
.authors.add {
  margin: 10px;
}
.authors.add.form-group {
  margin: 10px;
}
</style>