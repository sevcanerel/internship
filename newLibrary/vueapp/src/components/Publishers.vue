<template>
  <div class="publishers">
    <v-btn
      small
      v-if="add"
      v-on:click="newPublisher"
      elevation="20"
      color="#35938b"
      style="color:white;"
    >Add Publisher</v-btn>

    <input
      v-if="!hide"
      type="text"
      v-model="search"
      placeholder="Search Author"
      style="outline-color: #35938b; border:solid #35938b; "
    />
    <v-dialog width="600" v-model="edited" class="white">
      <v-card>
        <div class="edit">
          <div v-if="wrong" style="color:red;font-style:bold;">PLEASE FILL ALL AREAS</div>
          <div class="form-group">
            <label style="font-style:italic; color:#8D6E63; margin-left:5px; " for="name">Name:</label>
            <v-text-field
              type="text"
              style="margin:20px"
              id="name"
              v-model="publisher.name"
              placeholder="Enter to change Name"
            />
          </div>
          <div class="form-group">
            <label
              style="font-style:italic; color:#8D6E63; margin-left:5px; "
              for="address"
            >Address:</label>
            <v-text-field
              type="text"
              style="margin:20px"
              id="address"
              v-model="publisher.address"
              placeholder="Enter to change Address"
            />
          </div>
          <div class="form-group">
            <label style="font-style:italic; color:#8D6E63; margin-left:5px; " for="phone">Phone:</label>
            <v-text-field
              style="margin:20px"
              type="text"
              id="phone"
              v-model="publisher.phone"
              placeholder="Enter to change Phone"
            />
          </div>
          <v-card-actions>
            <v-btn
              small
              v-on:click="changePublisher(idVar)"
              elevation="20"
              color="#35938b"
              style="color:white;"
            >Change</v-btn>
            <v-btn
              small
              style="color:white;"
              elevation="20"
              color="brown lighten-3"
              v-on:click="changeQuit"
            >Quit</v-btn>
          </v-card-actions>
        </div>
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="submitted" class="white">
      <v-card>
        <div class="addPublisher">
          <div v-if="warning" style="color:red;font-style:bold;">THIS PUBLISHER EXISTS</div>
          <div v-if="wrong" style="color:red;font-style:bold;">PLEASE FILL ALL AREAS</div>
          <div class="form-group">
            <label style="font-style:italic; color:#8D6E63; margin-left:5px; " for="name">Name:</label>
            <v-text-field
              style="margin:20px"
              id="name"
              v-model="publisher.name"
              placeholder="Enter Name"
            />
          </div>
          <div class="form-group">
            <label style="font-style:italic; color:#8D6E63; margin-left:5px;" for="address">Address:</label>
            <v-text-field
              style="margin:20px"
              id="address"
              required
              v-model="publisher.address"
              placeholder="Enter Address"
            />
          </div>
          <div class="form-group">
            <label style="font-style:italic; color:#8D6E63; margin-left:5px;" for="phone">Phone:</label>
            <v-text-field
              style="margin:20px"
              id="phone"
              required
              v-model="publisher.phone"
              placeholder="Enter Phone"
            />
          </div>
          <v-card-actions>
            <v-btn
              small
              v-on:click="submitPublisher"
              elevation="20"
              color="#35938b"
              style="color:white;"
            >Submit</v-btn>
            <v-btn
              small
              style="color:white;"
              elevation="20"
              color="brown lighten-3"
              v-on:click="submitQuit"
            >Quit</v-btn>
          </v-card-actions>
        </div>
      </v-card>
    </v-dialog>

    <v-container>
      <ul>
        <v-row no-gutters>
          <div v-if="noPublishers" style="color:red">No Publisher is Found</div>
          <li
            style="list-style-type: none;"
            v-for="publisher in filteredPublishers"
            v-bind:key="publisher.id"
          >
            <v-col v-for="n in 1" :key="n" cols="sm">
              <v-card class="brown lighten-5">
                <div
                  style="font-style:italic; color:#8D6E63; padding:5px; padding-top:10px; padding-left:110px;"
                >
                  Name:
                  <p style="font-style:italic; color:black; display:inline">{{publisher.name}}</p>
                  <br />Phone:
                  <p style="font-style:italic; color:black; display:inline">{{publisher.phone}}</p>
                  <br />Address:
                  <p style="font-style:italic; color:black; display:inline">{{publisher.address}}</p>
                </div>
                <v-btn
                  small
                  style="color:white; margin-left:3px;"
                  elevation="20"
                  color="brown lighten-3"
                  v-on:click="deletePublisher(publisher.id)"
                >delete</v-btn>
                <v-btn
                  small
                  elevation="20"
                  color="#35938b"
                  style="color:white;"
                  v-on:click="edit(publisher.id,publisher.name,publisher.address,publisher.phone)"
                >edit</v-btn>
                <v-btn
                  small
                  elevation="20"
                  color="#35938b"
                  style="color:white;"
                  v-on:click="publisherAuthors(publisher.id,publisher.name)"
                >Author(s)</v-btn>
                <v-btn
                  small
                  elevation="20"
                  color="#35938b"
                  style="color:white;"
                  v-on:click="publisherBooks(publisher.id,publisher.name)"
                >Book(s)</v-btn>
                <br />
                <br />
              </v-card>
            </v-col>
          </li>
        </v-row>
      </ul>
    </v-container>
    <v-dialog width="600" v-model="displayAuthors" class="white">
      <v-card>
        <div class="showauthors">
          <div style="background-color:#00BFA5;   text-align: center; ">
            <h2 style="color:white; ">{{publisherHeader}}</h2>
          </div>
          <div
            v-if="noAuthors"
            class="noauthor"
            style="color:#795548;  font-weight:bold; text-align: center;  "
          >
            <p>No Author is Found</p>
          </div>

          <div>
            <v-container>
              <ul>
                <v-row no-gutters>
                  <li
                    style="list-style-type: none;"
                    v-for="author in allAuthors"
                    v-bind:key="author.id"
                  >
                    <v-col v-for="n in 1" :key="n" cols="sm">
                      <v-card class="brown lighten-5">
                        <br />
                        <div
                          style="font-style:italic; color:#8D6E63; padding-bottom:18px; padding-left:5px; padding-right:5px;"
                        >
                          <div style="text-align:center;"><h2>Author</h2></div>First Name:
                          <p
                            style="font-style:italic; color:black; display:inline"
                          >{{author.firstName}}</p>
                          <br />Last Name:
                          <p
                            style="font-style:italic; color:black; display:inline"
                          >{{author.lastName}}</p>
                        </div>
                      </v-card>
                    </v-col>
                  </li>
                </v-row>
              </ul>
            </v-container>

            <v-btn
              small
              style="margin-left:535px;color:white;"
              elevation="20"
              color="brown lighten-3"
              v-on:click="quitAuthors"
            >Quit</v-btn>
          </div>
        </div>
      </v-card>
    </v-dialog>
    <v-dialog width="600" v-model="displayBooks" class="white">
      <v-card>
        <div style="background-color:#00BFA5;   text-align: center; ">
          <h2 style="color:white; ">{{publisherHeader}}</h2>
        </div>
        <div
          v-if="noBooks"
          class="nobooks"
          style="color:#795548; font-weight:bold; text-align: center; "
        >
          <p>No Book is Found</p>
        </div>

        <div class="books">
          <v-container>
            <ul>
              <v-row no-gutters>
                <li style="list-style-type: none;" v-for="book in allBooks" v-bind:key="book.id">
                  <v-col v-for="n in 1" :key="n" cols="sm">
                    <v-card class="brown lighten-5" width="200px" height="400px">
                      <br />
                      <div style="font-style:italic; color:#8D6E63; text-align:center; ">
                        <div
                            style="text-align:center; padding-top:10px; margin-left:5px; margin-right:5px;"
                          >
                            <h2
                              style="font-style:italic; color:#8D6E63; display:inline"
                            >{{book.title}}</h2>
                          </div>
                          <br />
                        <p>
                          <img
                            :src="'http://localhost:8090/RestC/download/' + book.bookImg"
                            style="height:100px;width:140px;"
                          />
                        </p>
                        <br />Price:
                        <p style="font-style:italic; color:black; display:inline">{{book.price}}</p>
                        <br />Date:
                        <p
                          style="font-style:italic; color:black; display:inline"
                        >{{formatted(book.date)}}</p>
                        <br/>Author:
                        <p
                          style="font-style:italic; color:black; display:inline"
                        >{{book.author.firstName}} {{book.author.lastName}}</p>
                        <br />Category:
                        <p style="font-style:italic; color:black; display:inline">{{book.category}}</p>
                      </div>
                    </v-card>
                  </v-col>
                </li>
              </v-row>
            </ul>
          </v-container>

          <v-btn
            small
            elevation="20"
            color="brown lighten-3"
            style="margin-left:535px;color:white;"
            v-on:click="quitBooks"
          >Quit</v-btn>
        </div>
      </v-card>
    </v-dialog>
  </div>
</template>


<script>
export default {
  name: "publishers",
  data() {
    return {
      msg: "Show Publishers",
      noAuthors: false,
      noBooks: false,
      publisherHeader: "",
      hide: true,
      edited: false,
      add: true,
      search: "",
      warning: false,
      noPublishers: false,
      wrong: false,
      displayAuthors: false,
      displayBooks: false,
      idVar: 0,
      show: true,
      submitted: false,
      allPublishers: [],
      allAuthors: [],
      allBooks: [],
      publisher: {
        name: "",
        address: "",
        phone: ""
      },
      unchangedPublisher: {
        name: "",
        address: "",
        phone: ""
      }
    };
  },
  methods: {
    formatted: function(date) {
      var formatDate = date.substring(0, 10);
      return formatDate;
    },
    quitAuthors: function() {
      this.displayAuthors = false;
      this.allAuthors = {};
      this.showPublishers();
      this.add = true;
      this.noAuthors = false;
    },
    quitBooks: function() {
      this.displayBooks = false;

      this.add = true;
      this.showPublishers();
      this.noBooks = false;
    },
    publisherBooks: function(id, name) {
      this.$http
        .get("http://localhost:8090/RestC/PublishersBooks/" + id.toString())
        .then(response => {
          this.allBooks = response.data;
          this.publisherHeader = name;
          var deneme = JSON.stringify(response.data);
          if (deneme === "[]") {
            this.displayBooks = true;
            this.noBooks = true;
          } else {
            this.displayBooks = true;
            this.noBooks = false;
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    publisherAuthors: function(id, name) {
      this.$http
        .get("http://localhost:8090/RestC/PublishersAuthors/" + id.toString())
        .then(response => {
          this.allAuthors = response.data;
          this.publisherHeader = name;
          var deneme = JSON.stringify(response.data);
          if (deneme === "[]") {
            this.noAuthors = true;
            this.displayAuthors = true;
          } else {
            this.displayAuthors = true;
            this.noAuthors = false;
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    changeQuit: function() {
      this.edited = false;
      this.warning = false;
      this.hide = true;
      this.add = true;
      this.show = true;
      this.wrong = false;
      this.showPublishers();
    },
    submitQuit: function() {
      this.submitted = false;
      this.warning = false;
      this.hide = true;
      this.add = true;
      this.show = true;
      this.wrong = false;
      this.showPublishers();
    },
    hidePublishers: function() {
      this.hide = true;
      this.allPublishers = [];
      this.msg = "Show Publishers";
      this.show = true;
      this.noPublishers = false;
    },
    showPublishers: function() {
      this.msg = "reload";
      this.hide = false;
      this.show = false;
      this.$http
        .get("http://localhost:8090/RestC/orderPublishersByName")
        .then(response => {
          this.allPublishers = response.data;
          var deneme = JSON.stringify(response.data);
          if (deneme === "[]") {
            this.noPublishers = true;
          } else {
            this.noPublishers = false;
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    newPublisher() {
      this.show = true;
      this.submitted = true;

      this.publisher = {
        name: "",
        address: "",
        phone: ""
      };
    },
    submitPublisher() {
      this.warning = false;
      this.wrong = false;
      var data = {
        name: this.publisher.name,
        address: this.publisher.address,
        phone: this.publisher.phone
      };
      var publishersAll = {};
      this.$http
        .get("http://localhost:8090/RestC/getAllPublishers")
        .then(response => {
          this.publishersAll = response.data;
          for (var i = 0; i < this.publishersAll.length; i++) {
            if (
              this.publishersAll[i].name.trim() ===
                this.publisher.name.trim() &&
              this.publishersAll[i].phone.trim() ===
                this.publisher.phone.trim() &&
              this.publishersAll[i].address.trim() ===
                this.publisher.address.trim()
            ) {
              this.warning = true;
            }
          }
          if (
            this.publisher.name == "" ||
            this.publisher.name == null ||
            (this.publisher.phone == "" || this.publisher.phone == null) ||
            (this.publisher.address == "" || this.publisher.address == null)
          ) {
            this.wrong = true;
          } else {
            if (!this.warning) {
              this.submitted = false;
              this.$http
                .post("http://localhost:8090/RestC/addNewPublisher", data)
                .then(response => {
                  console.log(response.data);
                  this.showPublishers();
                  this.add = true;
                })
                .catch(err => {
                  console.log(err);
                });
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    deletePublisher: function(id) {
      this.$http
        .delete(
          "http://localhost:8090/RestC/deletePublisherById/" + id.toString()
        )
        .then(response => {
          console.log(response.data);
          this.showPublishers();
        })
        .catch(err => {
          console.log(err);
        });
    },
    edit: function(id, name1, address1, phone1) {
      this.edited = true;

      this.publisher = {
        name: name1,
        address: address1,
        phone: phone1
      };

      this.idVar = id;
    },
    changePublisher: function(id) {
      this.warning = false;
      this.wrong = false;
      var data = {
        name: this.publisher.name,
        address: this.publisher.address,
        phone: this.publisher.phone
      };

      if (
        this.publisher.name == "" ||
        this.publisher.name == null ||
        (this.publisher.phone == "" || this.publisher.phone == null) ||
        (this.publisher.address == "" || this.publisher.address == null)
      ) {
        this.wrong = true;
      } else {
        if (!this.wrong) {
          this.edited = false;
          this.$http
            .put(
              "http://localhost:8090/RestC/uptadePublisher/" + id.toString(),
              data
            )
            .then(response => {
              this.showPublishers();
              this.add = true;
            })
            .catch(err => {
              console.log(err);
            });
        }
      }
    }
  }, //methods
  computed: {
    filteredPublishers: function() {
      return this.allPublishers.filter(publisher => {
        var namePublisher = publisher.name;
        namePublisher = namePublisher.toLowerCase();
        return namePublisher.match(this.search.toLowerCase());
      });
    }
  },
  mounted() {
    this.showPublishers();
  }
}; //export default
</script>
<style scoped>
.publishers {
  margin: 20px;
}

.publishers.edit {
  padding: 20px;
}
.publishers.addPublisher {
  padding: 20px;
}
</style>