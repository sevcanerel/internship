<template>
  <v-app style="background-color:#E0E0E0">
  
    <div >
      <v-app-bar color="teal lighten-1" dark>
        <v-toolbar-title style="font-weight:bold;">{{pageTitle}}</v-toolbar-title>
        <div class="flex-grow-1"></div>
         <v-btn color="#00695C" style="font-weight:bold;" v-on:click="backFunction" v-if="back">Quit</v-btn>
        <v-btn color="#00695C" style="font-weight:bold;" v-on:click="booksFunction" v-if="booksButton">{{Books}}</v-btn>
        <v-btn
          color="#00695C"
          style="font-weight:bold;"
          v-on:click="authorsFunction"
          v-if="authorsButton"
        >{{Authors}}</v-btn>
        <v-btn
          color="#00695C"
          style="font-weight:bold;"
          v-on:click="publishersFunction"
          v-if="publishersButton"
        >{{Publishers}}</v-btn>
      </v-app-bar>
       <v-carousel v-if="pictures" height="658px" cycle >
    <v-carousel-item
      v-for="(item,i) in items"
      :key="i"
      :src="item.src"
      reverse-transition="fade-transition"
      transition="fade-transition"
    ></v-carousel-item>
  </v-carousel>
      <books v-if="booksPage"></books>
      <authors v-if="authorsPage"></authors>
      <publishers v-if="publishersPage"></publishers>
    </div>
  </v-app>
</template>

<script>
import authors from "@/components/Authors";
import books from "@/components/Books";
import publishers from "@/components/Publishers";
export default {
  name: "App",
  components: {
    authors,
    books,
    publishers
  },
  data() {
    return {
          items: [
          {
            src: 'https://imgstaticcontent.lbb.in/lbbnew/wp-content/uploads/2017/07/25115637/250717_Library%40Home.jpg' ,
          },
          {
            src: 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Duke_Humfrey%27s_Library_Interior_6%2C_Bodleian_Library%2C_Oxford%2C_UK_-_Diliff.jpg/1200px-Duke_Humfrey%27s_Library_Interior_6%2C_Bodleian_Library%2C_Oxford%2C_UK_-_Diliff.jpg',
          },
          {
            src: 'https://www.nationalgeographic.com/content/dam/travel/2017-digital/epic-libraries/iwaki-museum-of-picture-books-for-children-iwaki-japan.ngsversion.1505494539522.adapt.1900.1.jpg',
          },
          
        ],
       
      booksPage: false,
      authorsPage: false,
      publishersPage: false,
      pageTitle: "MainPage",
      authorsButton:true,
      booksButton:true,
      publishersButton:true,
      Books:"Books",
      Publishers:"Publishers",
      Authors:"Authors",
      back:false,
  
      pictures:true

    };
  },
  methods: {
    booksFunction: function() {
      this.booksPage = true;
      this.authorsPage = false;
      this.publishersPage = false;
      this.pageTitle = "Books";
      this.authorsButton=false;
      this.publishersButton=false;
      this.booksButton=false;
      this.back=true;
      this.pictures=false;
    },
    authorsFunction: function() {
      this.booksPage = false;
      this.authorsPage = true;
      this.publishersPage = false;
      this.pageTitle = "Authors";
      this.authorsButton=false;
      this.publishersButton=false;
      this.booksButton=false;
      this.back=true;
this.pictures=false;

    },
    publishersFunction: function() {

      this.booksPage = false;
      this.authorsPage = false;
      this.publishersPage = true;
      this.pageTitle = "Publishers";
      this.authorsButton=false;
      this.publishersButton=false;
      this.booksButton=false;
      this.back=true;
      this.pictures=false;
    },
    backFunction:function(){
      
      this.booksPage =false;
      this.authorsPage = false;
      this.publishersPage = false;
      this.pageTitle = "Main Page";
      this.authorsButton=true;
      this.publishersButton=true;
      this.booksButton=true;
      this.back=false;
      this.pictures=true;

    }
  }
};
</script>

<style>

</style>
