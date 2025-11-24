const express = require("express");
const http = require("http");
const fs = require("fs");
const bodyParser = require('body-parser')

const HTTP_PORT = 3000;

var app = express();
app.use(bodyParser.urlencoded({extended:true}))
app.use(bodyParser.json())

app.get('/users', function(req, res, next) {
  console.log(`recv ${req.method} ${req.url} port = ${req.secure ? HTTPS_PORT : HTTP_PORT}`)
  const data = JSON.parse(fs.readFileSync("./data/users-get.json", "utf8"));
  res.status(200).json(data);
  // res.status(400).json(data);
  // res.status(500).json(data);
})

app.post('/users', function(req, res, next) {
  console.log(`recv ${req.method} ${req.url} port = ${req.secure ? HTTPS_PORT : HTTP_PORT}`)
  console.log(`body ${JSON.stringify(req.body)}`)
  const data = JSON.parse(fs.readFileSync("./data/users-post.json", "utf8"));
  res.send(data);
})

http.createServer(app).listen(HTTP_PORT);

console.log(`server started... listening port is ${HTTP_PORT}`)
